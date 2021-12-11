package com.nashtech.rootkies.controllers.staff;

import com.nashtech.rootkies.constants.SuccessCode;
import com.nashtech.rootkies.converter.ReturnRequestConverter;
import com.nashtech.rootkies.dto.common.ResponseDTO;
import com.nashtech.rootkies.dto.returnrequest.CreateReturnRequestForUserDTO;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.model.ReturnRequest;
import com.nashtech.rootkies.service.ReturnRequestService;
import io.swagger.annotations.Api;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/staff/request")
@Api(tags = "Staff - request")
public class ReturnRequestController {

    @Autowired
    ReturnRequestService returnRequestService;

    @Autowired
    ReturnRequestConverter returnRequestConverter;

    @PostMapping
    public ResponseEntity<ResponseDTO> createReturnRequest(@Valid @RequestBody CreateReturnRequestForUserDTO dto)
            throws CreateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            response.setData(returnRequestConverter.convertToDTO(returnRequestService.createReturnRequestForUser(dto)));
            response.setSuccessCode(SuccessCode.REQUEST_CREATE_SUCCESS);
        }catch(Exception e){
            throw new CreateDataFailException(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }
}
