package com.nashtech.rootkies.service.impl;

import com.nashtech.rootkies.constants.ErrorCode;

import com.nashtech.rootkies.converter.UserConverter;

import com.nashtech.rootkies.dto.user.ViewDetailsUserDTO;
import com.nashtech.rootkies.dto.user.ViewUserDTO;
import com.nashtech.rootkies.dto.user.request.CreateUserDTO;

import com.nashtech.rootkies.dto.user.request.UpdatePasswordUserDTO;
import com.nashtech.rootkies.dto.user.request.UpdateUserDTO;
import com.nashtech.rootkies.enums.ERole;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DeleteDataFailException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.UpdateDataFailException;
import com.nashtech.rootkies.exception.UserAuthenticationException;
import com.nashtech.rootkies.exception.UserNotFoundException;

import com.nashtech.rootkies.model.Location;
import com.nashtech.rootkies.model.Role;
import com.nashtech.rootkies.model.User;

import com.nashtech.rootkies.payload.request.LoginRequest;
import com.nashtech.rootkies.payload.response.JwtResponse;
import com.nashtech.rootkies.repository.LocationRepository;
import com.nashtech.rootkies.repository.RoleRepository;
import com.nashtech.rootkies.repository.UserRepository;

import com.nashtech.rootkies.security.jwt.JwtUtils;
import com.nashtech.rootkies.security.services.UserDetailsImpl;
import com.nashtech.rootkies.service.AssignmentService;
import com.nashtech.rootkies.service.UserService;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.nashtech.rootkies.repository.specs.UserSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserConverter userConverter;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<ViewUserDTO> getListAllUserForAdmin(int pageNo, int pageSize, String valueSort, String direction, Long locationID) throws DataNotFoundException {
        List<ViewUserDTO> listDTO;

        try {
            Optional<Location> tempLocation = locationRepository.findById(locationID);

            Location location;

            if (tempLocation.isPresent()) {
                location = tempLocation.get();
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_LOCATION_NOT_FOUND);
            }

            List<Sort.Order> orders = new ArrayList<>();

            if (StringUtils.isNotEmpty(valueSort)) {
                orders.add(new Sort.Order("ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC,
                    valueSort));
            } else {
                orders.add(new Sort.Order(Sort.Direction.ASC, "firstName"));
            }

            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(orders));

            Page<User> page = userRepository.findAll(where(hasLocation(location))
                    .and(isNotDeleted()), pageable);

            listDTO = userConverter.toDTOList(page.getContent());
        } catch (Exception ex) {
            throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
        }

        return listDTO;
    }

    @Override
    public List<ViewUserDTO> getListSearchAndFilterUserForAdmin(int pageNo, int pageSize, String valueSort,
                                                                String direction, Long locationID, String searchBy, Long typeID) throws DataNotFoundException {
        List<ViewUserDTO> listDTO;

        try {
            Optional<Location> tempLocation = locationRepository.findById(locationID);

            Location location;

            if (tempLocation.isPresent()) {
                location = tempLocation.get();
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_LOCATION_NOT_FOUND);
            }

            Specification<User> conditions;

            if (Objects.nonNull(typeID)) {
                Role role;
                Optional<Role> tempType = roleRepository.findById(typeID);

                if (tempType.isPresent()) {
                    role = tempType.get();
                } else {
                    throw new DataNotFoundException(ErrorCode.ERR_LOCATION_NOT_FOUND);
                }

                if (StringUtils.isNotEmpty(searchBy)) {
                    conditions =
                            where(hasLocation(location))
                                    .and(nameContainsIgnoreCase(searchBy))
                                    .and(hasType(role))
                                    .and(isNotDeleted());
                } else {
                    conditions = where(hasLocation(location))
                            .and(hasType(role))
                            .and(isNotDeleted());
                }

            } else {
                if (StringUtils.isNotEmpty(searchBy)) {
                    conditions =
                        where(hasLocation(location)).and(nameContainsIgnoreCase(searchBy)).and(isNotDeleted());
                } else {
                    conditions =
                        where(hasLocation(location)).and(isNotDeleted());
                }
            }

            List<Sort.Order> orders = new ArrayList<>();

            if (StringUtils.isNotEmpty(valueSort)) {
                orders.add(new Sort.Order("ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC, valueSort));
            } else {
                orders.add(new Sort.Order(Sort.Direction.ASC, "firstName"));
            }

            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(orders));

            Page<User> page = userRepository.findAll(conditions, pageable);

            listDTO = userConverter.toDTOList(page.getContent());
        } catch (Exception ex) {
            throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
        }

        return listDTO;
    }

    @Override
    public int getRecordListAllUser(Long locationID) throws DataNotFoundException {
        int counter;

        try {
            Optional<Location> tempLocation = locationRepository.findById(locationID);
            Location location;

            if (tempLocation.isPresent()) {
                location = tempLocation.get();
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_LOCATION_NOT_FOUND);
            }

            List<User> listUser = userRepository.findAll(where(hasLocation(location))
                    .and(isNotDeleted()));

            List<ViewUserDTO> listDTO = userConverter.toDTOList(listUser);

            counter = listDTO.size();
        } catch (Exception ex) {
            throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
        }

        return counter;
    }

    @Override
    public int getRecordListSearchAndFilterUser(Long locationID, String searchBy, Long typeID) throws DataNotFoundException {
        int counter;

        try {
            Optional<Location> tempLocation = locationRepository.findById(locationID);
            Location location;

            if (tempLocation.isPresent()) {
                location = tempLocation.get();
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_LOCATION_NOT_FOUND);
            }

            Specification<User> conditions;

            if (Objects.nonNull(typeID)) {
                Role role;
                Optional<Role> tempType = roleRepository.findById(typeID);

                if (tempType.isPresent()) {
                    role = tempType.get();
                } else {
                    throw new DataNotFoundException(ErrorCode.ERR_LOCATION_NOT_FOUND);
                }

                if (StringUtils.isNotEmpty(searchBy)) {
                    conditions =
                            where(hasLocation(location))
                                    .and(nameContainsIgnoreCase(searchBy))
                                    .and(hasType(role))
                                    .and(isNotDeleted());
                } else {
                    conditions = where(hasLocation(location))
                            .and(hasType(role))
                            .and(isNotDeleted());
                }

            } else {
                if (StringUtils.isNotEmpty(searchBy)) {
                    conditions =
                        where(hasLocation(location)).and(nameContainsIgnoreCase(searchBy)).and(isNotDeleted());
                } else {
                    conditions =
                        where(hasLocation(location)).and(isNotDeleted());
                }
            }

            List<User> listUser = userRepository.findAll(conditions);

            List<ViewUserDTO> listDTO = userConverter.toDTOList(listUser);

            counter = listDTO.size();
        } catch (Exception ex) {
            throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
        }

        return counter;
    }

    @Override
    public User createNewUser(CreateUserDTO dto) throws CreateDataFailException {
        User user;

        try {
            user = userConverter.convertToEntityCreate(dto);
            return userRepository.save(user);
        } catch (Exception ex) {
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_USER_FAIL);
        }
    }

    @Override
    public ViewDetailsUserDTO getUserDetailsToShow(String code) throws DataNotFoundException {
        ViewDetailsUserDTO dto;

        try {
            User user;
            Optional<User> tempUser = userRepository.getUserByCode(code);

            if (tempUser.isPresent()) {
                user = tempUser.get();
            } else {
                throw new UserNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
            }

            dto = userConverter.convertToViewDetailsDTO(user);
        } catch (Exception ex) {
            throw new DataNotFoundException(ErrorCode.ERR_USER_LOADED_FAIL);
        }

        return dto;
    }

    @Override
    public int disableUser(String code) throws UserNotFoundException, DeleteDataFailException, DataNotFoundException {
        //find user by id
        Optional<User> optionalUser = userRepository.getUserByCode(code);

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
        }

        boolean check = assignmentService.checkIfValidAssignment(code);
        if (check){
            throw new DeleteDataFailException(ErrorCode.ERROR_USER_SIGNED_ASSIGMENT);
        }

        //delete user
        int result = userRepository.disableUser(code);
        if (result == 0) {
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_USER_FAIL);
        }
        return result;
    }


    @Override
    public User updateUser(UpdateUserDTO updateUserDTO, String id) throws UpdateDataFailException, DataNotFoundException {
        try {
            Optional<User> existedUser = userRepository.findById(id);
            if (!existedUser.isPresent()) {
                LOGGER.info("User {} not found", id);
                throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
            }

            int yearOld = (LocalDateTime.now().getYear()) - (updateUserDTO.getDob().getYear());
            if (yearOld < 18) {
                LOGGER.info("User {} must older than 18", id);
                throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_DOB_USER_FAIL);
            }

            boolean isAfter = updateUserDTO.getJoinedDate().isAfter(updateUserDTO.getDob());
            if (!isAfter) {
                LOGGER.info("JoinDate {} must later than DateOfBirth", id);
                throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_JOINDATE_USER_FAIL);
            }
            Role tempRole = roleRepository.getById(updateUserDTO.getRoleId());
            User user = existedUser.get();
            user.setDob(updateUserDTO.getDob());
            user.setGender(updateUserDTO.getGender());
            user.setJoinedDate(updateUserDTO.getJoinedDate());
            user.setRole(tempRole);
            return userRepository.save(user);
        } catch (DataNotFoundException e) {
            String message = e.getMessage();
            if (message.equals(ErrorCode.ERR_UPDATE_DOB_USER_FAIL)) {
                throw new DataNotFoundException(ErrorCode.ERR_UPDATE_DOB_USER_FAIL);
            } else if (message.equals(ErrorCode.ERR_UPDATE_JOINDATE_USER_FAIL)) {
                throw new DataNotFoundException(ErrorCode.ERR_UPDATE_JOINDATE_USER_FAIL);
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.info("Fail to update user {}", id);
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_USER_FAIL);

        }
    }

    @Override
    public User updatePasswordUserFirstLogin(UpdatePasswordUserDTO updateUserDTO) throws UpdateDataFailException, DataNotFoundException {
        try {
            Optional<User> existedUser = userRepository.findById(updateUserDTO.getCode());
            if (!existedUser.isPresent()) {
                LOGGER.info("User {} not found", updateUserDTO.getCode());
                throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
            }

            User user = existedUser.get();
            user.setPassword(encoder.encode(updateUserDTO.getPassword()));
            user.setNew(false);

            return userRepository.save(user);
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
        } catch (Exception e) {
            LOGGER.info("Fail to update user {}", updateUserDTO.getCode());
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_USER_FAIL);
        }
    }

    @Override
    public boolean updatePassword(UpdatePasswordUserDTO updateUserDTO) throws UpdateDataFailException, DataNotFoundException {
        boolean result = false;
        try {
            Optional<User> existedUser = userRepository.findById(updateUserDTO.getCode());
            if (!existedUser.isPresent()) {
                LOGGER.info("User {} not found", updateUserDTO.getCode());
                throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
            }
            User user = existedUser.get();
            boolean check = encoder.matches(updateUserDTO.getOldPassword(), user.getPassword());
            if (check) {
                user.setPassword(encoder.encode(updateUserDTO.getPassword()));
                userRepository.save(user);
                result = true;
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_WRONG_OLD_PASSWORD);
            }
        } catch (DataNotFoundException ex) {
          throw new DataNotFoundException(ex.getMessage());
        } catch (Exception e) {
            LOGGER.info("Fail to update user {}", updateUserDTO.getCode());
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_USER_FAIL);
        }
        return result;
    }

    @Override
    public boolean checkDeletedAccount(String code) throws UserNotFoundException, DataNotFoundException {
        boolean result = false;
        try {
            User user;
            Optional<User> optionalUser = userRepository.getUserByCode(code);
            if (!optionalUser.isPresent()) {
                throw new UserNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
            } else {
                user = optionalUser.get();
            }
            result = user.isDeleted();
        } catch (Exception e) {
            throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
        }
        return result;
    }

    @Override
    public JwtResponse authenticateAccount(LoginRequest loginRequest) throws UserAuthenticationException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String roles = userDetails.getAuthorities().toString();
            String cutRole = roles.substring(1, 11);
            ERole roleName = ERole.valueOf(cutRole);
            Role role;
            Optional<Role> tempRole = roleRepository.findByName(roleName);
            if (tempRole.isPresent()) {
                role = tempRole.get();
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_ROLE_NOT_FOUND);
            }
            User user = userRepository.findByCode(userDetails.getCode());
            return new JwtResponse(jwt, userDetails.getCode(),
                    userDetails.getUsername(),
                    userDetails.getFullName(),
                    role,
                    user.getLocation(),
                    user.isDeleted(),
                    user.isNew());
        } catch (Exception e) {
            LOGGER.info("Account Authentication Error: " + e.getMessage());
            throw new UserAuthenticationException(ErrorCode.ERR_USER_LOGIN_FAIL);
        }
    }

}