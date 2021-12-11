package com.nashtech.rootkies.converter;

import com.nashtech.rootkies.constants.ErrorCode;

import com.nashtech.rootkies.dto.user.ViewDetailsUserDTO;
import com.nashtech.rootkies.dto.user.ViewUserDTO;
import com.nashtech.rootkies.dto.user.request.RoleDTO;
import com.nashtech.rootkies.dto.user.request.CreateUserDTO;

import com.nashtech.rootkies.dto.user.request.UpdateUserDTO;
import com.nashtech.rootkies.exception.ConvertEntityDTOException;

import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.model.Location;
import com.nashtech.rootkies.model.Role;
import com.nashtech.rootkies.model.User;

import com.nashtech.rootkies.enums.ERole;

import com.nashtech.rootkies.repository.RoleRepository;
import com.nashtech.rootkies.repository.LocationRepository;
import com.nashtech.rootkies.repository.UserRepository;

import org.modelmapper.ModelMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Locale;

import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class UserConverter {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    PasswordEncoder encoder;

    public ViewUserDTO convertToViewDTO(User user) {
        ViewUserDTO dto = modelMapper.map(user, ViewUserDTO.class);
        dto.setFullName(user.getFirstName() + " " + user.getLastName());
        dto.setRoleName(user.getRole().getName().equals(ERole.ROLE_ADMIN) ? "Admin" : "Staff");
        dto.setLocationId(user.getLocation().getId());
        return dto;
    }

    public ViewDetailsUserDTO convertToViewDetailsDTO(User user) {
        ViewDetailsUserDTO dto = modelMapper.map(user, ViewDetailsUserDTO.class);
        dto.setLocationName(user.getLocation().getName());
        dto.setRole(convertToRoleDTO(user.getRole()));
        dto.setLocationName(user.getLocation().getName());
        return dto;
    }

    public RoleDTO convertToRoleDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    public List<ViewUserDTO> toDTOList(List<User> listEntity) throws ConvertEntityDTOException {
        List<ViewUserDTO> listDTO;

        try {
            listDTO = listEntity.stream().map(this::convertToViewDTO).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

        return listDTO;
    }

    private String splitWord(String w) {
        String[] words = w.split(" ");

        StringBuilder result = new StringBuilder();

        for (String s : words) {
            result.append(s.charAt(0));
        }

        return result.toString();
    }

    private String checkUsername(String name) {
        String final_name = "";

        if (userRepository.existsByUsername(name)) {
            int number = 1;
            String temp_name = name + number;

            while (userRepository.existsByUsername(temp_name)) {
                number += 1;
                temp_name = name + number;
            }

            final_name += temp_name;
        } else {
            final_name += name;
        }

        return final_name;
    }

    public User convertToEntityCreate(CreateUserDTO dto) throws ConvertEntityDTOException {
        try {
            User user = modelMapper.map(dto, User.class);

            String username =
                    dto.getFirstName().toLowerCase(Locale.ROOT) + splitWord(dto.getLastName()).toLowerCase(Locale.ROOT);
            String finalName = checkUsername(username);
            user.setUsername(finalName);

            String password = finalName + "@" + dto.getDob().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
            user.setPassword(encoder.encode(password));

            Optional<Location> optLoc = locationRepository.findById(dto.getLocationId());
            Location location;
            if (optLoc.isPresent()) {
                location = optLoc.get();
            } else throw new DataNotFoundException(ErrorCode.ERR_LOCATION_NOT_FOUND);
            user.setLocation(location);

            Optional<Role> optRole = roleRepository.findByName(dto.getRole().getName());
            Role role;
            if (optRole.isPresent()) {
                role = optRole.get();
            } else throw new DataNotFoundException(ErrorCode.ERR_ROLE_NOT_FOUND);
            user.setRole(role);

            user.setNew(true);
            user.setDeleted(false);

            return user;
        } catch (Exception ex) {
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERT_DTO_ENTITY_FAIL);
        }
    }

    public UpdateUserDTO convertToDTOUpdate(User user) throws ConvertEntityDTOException {
        try {
            UpdateUserDTO dto = modelMapper.map(user, UpdateUserDTO.class);
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setDob(user.getDob());
            dto.setGender(user.getGender());
            dto.setJoinedDate(user.getJoinedDate());
            dto.setRoleId(user.getRole().getId());
            return dto;
        } catch (Exception ex) {
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERT_DTO_ENTITY_FAIL);
        }
    }
}
