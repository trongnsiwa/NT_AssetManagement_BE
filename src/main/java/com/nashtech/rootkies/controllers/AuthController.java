package com.nashtech.rootkies.controllers;


import javax.validation.Valid;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.constants.SuccessCode;
import com.nashtech.rootkies.converter.UserConverter;
import com.nashtech.rootkies.dto.common.ResponseDTO;
import com.nashtech.rootkies.dto.user.request.UpdatePasswordUserDTO;
import com.nashtech.rootkies.dto.user.request.UpdateUserDTO;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.UpdateDataFailException;
import com.nashtech.rootkies.exception.UserAuthenticationException;
import com.nashtech.rootkies.model.User;
import com.nashtech.rootkies.payload.request.LoginRequest;
import com.nashtech.rootkies.payload.response.JwtResponse;
import com.nashtech.rootkies.service.UserService;

import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Api(tags = "Auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    UserConverter userConverter;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest login) throws UserAuthenticationException {
        ResponseDTO response = new ResponseDTO();
        try {
            JwtResponse jwtRes = userService.authenticateAccount(login);
            response.setData(jwtRes);
            response.setSuccessCode(SuccessCode.USER_LOGIN_SUCCESS);
        } catch (Exception ex) {
            LOGGER.info("Having error when login: " + ex.getMessage());
            throw new UserAuthenticationException(ErrorCode.ERR_USER_LOGIN_FAIL);
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePasswordFirstTime(@RequestBody UpdatePasswordUserDTO dto) throws UpdateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            User user = userService.updatePasswordUserFirstLogin(dto);
            UpdateUserDTO responseProduct = userConverter.convertToDTOUpdate(user);
            response.setData(responseProduct);
            response.setSuccessCode(SuccessCode.USER_CHANGE_PASSWORD_SUCCESS);
        } catch (Exception ex) {
            LOGGER.info("Having error when update password account: " + ex.getMessage());
            throw new UpdateDataFailException(ErrorCode.ERR_USER_CHANGE_PASSWORD_FAIL);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-deleted/{code}")
    public ResponseEntity<ResponseDTO> checkDeteleAccount(@PathVariable String code) throws UpdateDataFailException, DataNotFoundException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result = userService.checkDeletedAccount(code);
            response.setData(result);
            response.setSuccessCode(SuccessCode.USER_GET_STATUS_DELETE_SUCCESS);
        } catch (DataNotFoundException ex) {
            throw new DataNotFoundException(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.info("Having error when update password account: " + ex.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

}
