package com.nashtech.rootkies.controllers.staff;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.constants.SuccessCode;
import com.nashtech.rootkies.controllers.AuthController;
import com.nashtech.rootkies.dto.common.ResponseDTO;
import com.nashtech.rootkies.dto.user.request.UpdatePasswordUserDTO;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.UpdateDataFailException;
import com.nashtech.rootkies.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/staff/user")
@Api(tags = "Staff - User")
public class UserControllerStaff {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserService userService;

    @PutMapping("/change-password-inside")
    public ResponseEntity<?> changePassword(@RequestBody UpdatePasswordUserDTO dto) throws UpdateDataFailException, DataNotFoundException {
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
