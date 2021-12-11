package com.nashtech.rootkies.controllers.admin;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.constants.SuccessCode;
import com.nashtech.rootkies.dto.common.ResponseDTO;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.service.HistoricalAssignmentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/history")
@Api(tags = "Historical Assignment")
public class HistoricalAssignmentController {

    @Autowired
    HistoricalAssignmentService historicalAssignmentService;

    @GetMapping("/asset/{id}")
    public ResponseEntity<ResponseDTO> checkAssetInHistory(@PathVariable("id") Long id) throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean checkAssignment = historicalAssignmentService.checkAssetInHistory(id);
            response.setData(checkAssignment);
            if (checkAssignment) {
                response.setErrorCode(ErrorCode.ERR_ASSET_EXISTS_IN_HISTORY_ASSIGNMENT);
            } else {
                response.setSuccessCode(SuccessCode.ASSET_DELETE_SUCCESS);
            }
        } catch (Exception e) {
            throw new DataNotFoundException(ErrorCode.ERR_ASSET_EXISTS_IN_HISTORY_ASSIGNMENT);
        }
        return ResponseEntity.ok().body(response);
    }

}
