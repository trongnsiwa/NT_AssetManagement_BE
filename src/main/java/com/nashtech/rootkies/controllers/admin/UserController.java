package com.nashtech.rootkies.controllers.admin;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.constants.SuccessCode;
import com.nashtech.rootkies.converter.CategoryConverter;
import com.nashtech.rootkies.converter.UserConverter;
import com.nashtech.rootkies.dto.common.ResponseDTO;
import com.nashtech.rootkies.dto.user.ViewDetailsUserDTO;
import com.nashtech.rootkies.dto.user.ViewUserDTO;
import com.nashtech.rootkies.dto.user.request.CreateUserDTO;
import com.nashtech.rootkies.dto.user.request.UpdatePasswordUserDTO;
import com.nashtech.rootkies.dto.user.request.UpdateUserDTO;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.DeleteDataFailException;
import com.nashtech.rootkies.exception.UpdateDataFailException;
import com.nashtech.rootkies.model.User;
import com.nashtech.rootkies.service.AssignmentService;
import com.nashtech.rootkies.service.CategoryService;
import com.nashtech.rootkies.service.UserService;

import io.swagger.annotations.Api;

import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/user")
@Api(tags = "Admin - User")
public class UserController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    CategoryConverter categoryConverter;

    @Autowired
    UserConverter userConverter;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/getList")
    public ResponseEntity<ResponseDTO> getListUser(
            @RequestParam("page") @Min(1) Integer page,
            @RequestParam("size") @Min(1) Integer size,
            @RequestParam("sort") String sortValue,
            @RequestParam("direction") String direction,
            @RequestParam("location") @Min(1) Integer location
    ) throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();

        try {
            Long locationID = Long.valueOf(String.valueOf(location));

            List<ViewUserDTO> listDTO = userService.getListAllUserForAdmin(page, size, sortValue.trim(), direction.trim(),
                locationID);

            if (listDTO.size() > 0) {
                response.setData(listDTO);
                response.setSuccessCode(SuccessCode.USER_LIST_LOADED_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_USER_LIST_EMPTY);
            }
        } catch (Exception ex) {
            LOGGER.info("Having error when loading list user: " + ex.getMessage());
            response.setErrorCode(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
        }

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> getListSearchAndFilterUser(
            @RequestParam("page") @Min(1) Integer page,
            @RequestParam("size") @Min(1) Integer size,
            @RequestParam("sort") String sortValue,
            @RequestParam("direction") String direction,
            @RequestParam("location") @Min(1) Integer location,
            @RequestParam("searchedBy") String searchedBy,
            @RequestParam("type") @Min(0) Integer type
    ) throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();

        try {
            Long locationID = Long.valueOf(String.valueOf(location));
            Long typeID = null;

            if (type != 0) {
                typeID = Long.valueOf(String.valueOf(type));
            }

            List<ViewUserDTO> listDTO = userService.getListSearchAndFilterUserForAdmin(page, size, sortValue.trim(),
                direction.trim(), locationID, searchedBy.trim(), typeID);

            if (listDTO.size() > 0) {
                response.setData(listDTO);
                response.setSuccessCode(SuccessCode.USER_LIST_LOADED_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_USER_LIST_EMPTY);
            }

        } catch (Exception ex) {
            LOGGER.info("Having error when loading list user: " + ex.getMessage());
            response.setErrorCode(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
        }

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/record")
    public ResponseEntity<ResponseDTO> getListUserRecord(@RequestParam("location") @Min(1) Integer location) throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();

        try {
            Long locationID = Long.valueOf(String.valueOf(location));

            int value = userService.getRecordListAllUser(locationID);

            if (value > 0) {
                response.setData(value);
                response.setSuccessCode(SuccessCode.USER_LIST_LOADED_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_USER_LIST_EMPTY);
            }
        } catch (Exception ex) {
            LOGGER.info("Having error when loading list user: " + ex.getMessage());
            response.setErrorCode(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
        }

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search/record")
    public ResponseEntity<ResponseDTO> getListSearchAndFilterUserRecord(
            @RequestParam("location") @Min(1) Integer location,
            @RequestParam("searchedBy") String searchedBy,
            @RequestParam("type") @Min(0) Integer type
    ) throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();

        try {
            Long locationID = Long.valueOf(String.valueOf(location));
            Long typeID = null;

            if (type != 0) {
                typeID = Long.valueOf(String.valueOf(type));
            }

            int value = userService.getRecordListSearchAndFilterUser(locationID, searchedBy.trim(), typeID);

            if (value > 0) {
                response.setData(value);
                response.setSuccessCode(SuccessCode.USER_LIST_LOADED_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_USER_LIST_EMPTY);
            }
        } catch (Exception ex) {
            LOGGER.info("Having error when loading list user: " + ex.getMessage());
            response.setErrorCode(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
        }

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ResponseDTO> getUserDetail(@PathVariable("code") String code) throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();

        try {
            ViewDetailsUserDTO dto = userService.getUserDetailsToShow(code);

            if (Objects.nonNull(dto)) {
                response.setData(dto);
                response.setSuccessCode(SuccessCode.USER_LOADED_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_USER_NOT_FOUND);
            }

        } catch (Exception ex) {
            LOGGER.info("Having error when loading user: " + ex.getMessage());
            response.setErrorCode(ErrorCode.ERR_USER_LOADED_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_USER_LOADED_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/disable/{code}")
    public ResponseEntity<ResponseDTO> disableUser(@PathVariable("code") String code) throws DeleteDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            userService.disableUser(code);
            int result = userService.disableUser(code);
            response.setData(result);
            response.setSuccessCode(SuccessCode.USER_DELETE_SUCCESS);
        } catch (Exception e) {
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_USER_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createUser(@Valid @RequestBody CreateUserDTO dto)
            throws CreateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            User user = userService.createNewUser(dto);
            ViewUserDTO userDTO = userConverter.convertToViewDTO(user);
            response.setData(userDTO);
            response.setSuccessCode(SuccessCode.USER_CREATED_SUCCESS);
        } catch (Exception ex) {
            response.setErrorCode(ErrorCode.ERR_CREATE_USER_FAIL);
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_USER_FAIL);
        }

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateUser(
            @Valid @RequestBody UpdateUserDTO updateUserDTO)
            throws UpdateDataFailException, DataNotFoundException {
        ResponseDTO response = new ResponseDTO();

        try {
            User updatedUser =
                    userService.updateUser(updateUserDTO, updateUserDTO.getCode());

            ViewUserDTO responseProduct = userConverter.convertToViewDTO(updatedUser);

            response.setData(responseProduct);
            response.setSuccessCode(SuccessCode.USER_UPDATE_SUCCESS);
        } catch (DataNotFoundException e) {
            String message = e.getMessage();

            if (message.equals(ErrorCode.ERR_UPDATE_DOB_USER_FAIL)) {
                response.setErrorCode(ErrorCode.ERR_UPDATE_DOB_USER_FAIL);
                throw new DataNotFoundException(ErrorCode.ERR_UPDATE_DOB_USER_FAIL);
            } else if (message.equals(ErrorCode.ERR_UPDATE_JOINDATE_USER_FAIL)) {
                response.setErrorCode(ErrorCode.ERR_UPDATE_JOINDATE_USER_FAIL);
                throw new DataNotFoundException(ErrorCode.ERR_UPDATE_JOINDATE_USER_FAIL);
            } else {
                response.setErrorCode(ErrorCode.ERR_USER_NOT_FOUND);
                throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_UPDATE_USER_FAIL);
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_USER_FAIL);
        }

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody UpdatePasswordUserDTO dto) throws UpdateDataFailException, DataNotFoundException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result = userService.updatePassword(dto);
            response.setData(result);
            response.setSuccessCode(SuccessCode.USER_CHANGE_PASSWORD_SUCCESS);
        } catch (DataNotFoundException ex) {
            throw new DataNotFoundException(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.info("Having error when update password account: " + ex.getMessage());
            throw new UpdateDataFailException(ErrorCode.ERR_USER_CHANGE_PASSWORD_FAIL);
        }
        return ResponseEntity.ok(response);
    }

}
