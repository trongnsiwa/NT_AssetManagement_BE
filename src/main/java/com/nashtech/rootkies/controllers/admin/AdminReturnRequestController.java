package com.nashtech.rootkies.controllers.admin;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.constants.SuccessCode;
import com.nashtech.rootkies.converter.ReturnRequestConverter;
import com.nashtech.rootkies.dto.common.ResponseDTO;
import com.nashtech.rootkies.dto.returnrequest.CreateRequestDTO;
import com.nashtech.rootkies.dto.returnrequest.ViewReturnRequestDTO;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.DeleteDataFailException;
import com.nashtech.rootkies.model.HistoricalAssignment;
import com.nashtech.rootkies.model.ReturnRequest;
import com.nashtech.rootkies.service.AssignmentService;
import com.nashtech.rootkies.service.HistoricalAssignmentService;
import com.nashtech.rootkies.service.ReturnRequestService;
import com.nashtech.rootkies.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/request")
@Api(tags = "Admin - request")
public class AdminReturnRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminReturnRequestController.class);

    @Autowired
    ReturnRequestService returnRequestService;

    @Autowired
    ReturnRequestConverter returnRequestConverter;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    UserService userService;

    @Autowired
    HistoricalAssignmentService historicalAssignmentService;

    @GetMapping("/getList")
    public ResponseEntity<ResponseDTO> getListRequest(
            @RequestParam("page") @Min(1) Integer page,
            @RequestParam("size") @Min(1) Integer size,
            @RequestParam("sort") String sortValue,
            @RequestParam("direction") String direction)
            throws DataNotFoundException{
        ResponseDTO response = new ResponseDTO();
        try {
            List<ViewReturnRequestDTO> listRequest = returnRequestService.getAllRequestForAdmin(page, size, sortValue.trim(), direction.trim());
            response.setSuccessCode(SuccessCode.REQUEST_LOAD_LIST_SUCCESS);
            response.setData(listRequest);
        }catch(Exception e){
            LOGGER.info("Having error when loading list Request: " + e.getMessage());
            response.setErrorCode(ErrorCode.ERR_LOAD_LIST_REQUEST_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_LOAD_LIST_REQUEST_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getRecord")
    public ResponseEntity<ResponseDTO> getRecordAllRequest()
            throws DataNotFoundException{
        ResponseDTO response = new ResponseDTO();
        try {
            int count = returnRequestService.recordAllRequestForAdmin();
            response.setSuccessCode(SuccessCode.REQUEST_LOAD_LIST_SUCCESS);
            response.setData(count);
        }catch(Exception e){
            LOGGER.info("Having error when loading list Request: " + e.getMessage());
            response.setErrorCode(ErrorCode.ERR_LOAD_LIST_REQUEST_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_LOAD_LIST_REQUEST_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/searchRequest")
    public ResponseEntity<ResponseDTO> searchRequest(
            @RequestParam("page") @Min(1) Integer page,
            @RequestParam("size") @Min(1) Integer size,
            @RequestParam("sort") String sortValue,
            @RequestParam("direction") String direction,
            @RequestParam(value = "state", required = false) Boolean state,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "returnedDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime returnedDate)
        throws DataNotFoundException{
        ResponseDTO response = new ResponseDTO();
        try {
            List<ViewReturnRequestDTO> listRequest = returnRequestService.searchRequest(page, size, sortValue.trim(), direction.trim(), state, returnedDate, keyword);
            response.setData(listRequest);
            response.setSuccessCode(SuccessCode.REQUEST_LOAD_LIST_SUCCESS);
        }catch(Exception e){
            LOGGER.info("Having error when loading list Request: " + e.getMessage());
            response.setErrorCode(ErrorCode.ERR_LOAD_LIST_REQUEST_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_REQUEST_LIST_NOT_FOUND);
        }

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/recordSearch")
    public ResponseEntity<ResponseDTO> getRecordSearchRequest(
            @RequestParam(value = "state", required = false) Boolean state,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "returnedDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime returnedDate)
            throws DataNotFoundException{
        ResponseDTO response = new ResponseDTO();
        try {
            int listRequest = returnRequestService.recordSearchRequest(state, returnedDate, keyword);
            response.setData(listRequest);
            response.setSuccessCode(SuccessCode.REQUEST_LOAD_LIST_SUCCESS);
        }catch(Exception e){
            LOGGER.info("Having error when loading list Request: " + e.getMessage());
            response.setErrorCode(ErrorCode.ERR_LOAD_LIST_REQUEST_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_REQUEST_LIST_NOT_FOUND);
        }

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/last_date")
    public ResponseEntity<ResponseDTO> getReturnDateDateLast() throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();
        try {
            LocalDateTime value = returnRequestService.getReturnDateLastest();
            if (Objects.nonNull(value)) {
                response.setData(value);
                response.setSuccessCode(SuccessCode.REQUEST_LOAD_LIST_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_LOAD_LIST_REQUEST_FAIL);
            }
        } catch (Exception ex) {
            LOGGER.info("Having error when loading list Request: " + ex.getMessage());
            response.setErrorCode(ErrorCode.ERR_LOAD_LIST_REQUEST_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_LOAD_LIST_REQUEST_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createNewRequest(@Valid @RequestBody CreateRequestDTO dto) throws CreateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            ReturnRequest request = returnRequestService.createReturnRequest(dto);
            ViewReturnRequestDTO viewRequest = returnRequestConverter.convertToDTO(request);
            response.setData(viewRequest);
            response.setSuccessCode(SuccessCode.REQUEST_CREATE_SUCCESS);
        }catch (Exception ex) {
            LOGGER.info("Having error when loading list Request: " + ex.getMessage());
            response.setErrorCode(ErrorCode.ERR_CREATE_REQUEST_FAIL);
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_REQUEST_FAIL);
        }

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("accept/{requestId}/{acceptedBy}")
    public ResponseEntity<ResponseDTO> acceptReturnRequestByAdmin(
            @PathVariable("requestId") Long requestId,
            @PathVariable("acceptedBy") String acceptedBy) throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();
        try {
            ReturnRequest returnRequest= returnRequestService.acceptRequest(requestId,acceptedBy);
            HistoricalAssignment historicalAssignment= historicalAssignmentService.createWhenReqAccept(returnRequest);
            ViewReturnRequestDTO viewReturnRequestDTO= returnRequestConverter.convertToDTO(returnRequest);
            if (Objects.nonNull(returnRequest)) {
                response.setData(viewReturnRequestDTO);
                response.setSuccessCode(SuccessCode.RETURN_REQUEST_ACCEPT_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_RETURN_REQUEST_ACCEPT_FAIL);
            }
        } catch (Exception ex) {
            LOGGER.info("Having error when Accepting Request: " + ex.getMessage());
            response.setErrorCode(ErrorCode.ERR_RETURN_REQUEST_ACCEPT_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_RETURN_REQUEST_ACCEPT_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<ResponseDTO> cancelReturnRequest(@PathVariable("id") Long id) throws DeleteDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean value = returnRequestService.cancelRequest(id);
            if (value == true) {
                response.setData(value);
                response.setSuccessCode(SuccessCode.RETURN_REQUEST_CANCEL_SUCCESS);
            }
        } catch (Exception ex) {
            throw new DeleteDataFailException(ex.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }
}
