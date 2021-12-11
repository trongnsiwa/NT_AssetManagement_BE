package com.nashtech.rootkies.controllers;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.constants.SuccessCode;
import com.nashtech.rootkies.converter.AssignmentConverter;
import com.nashtech.rootkies.converter.ReturnRequestConverter;
import com.nashtech.rootkies.dto.assigment.AssignmentDTO;
import com.nashtech.rootkies.dto.assigment.ViewDetailAssignmentDTO;
import com.nashtech.rootkies.dto.common.ResponseDTO;
import com.nashtech.rootkies.dto.returnrequest.CreateReturnRequestForUserDTO;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.UserNotFoundException;
import com.nashtech.rootkies.model.Assignment;
import com.nashtech.rootkies.service.AssignmentService;
import com.nashtech.rootkies.service.HomeService;
import com.nashtech.rootkies.service.ReturnRequestService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/home")
@Api(tags = "Home")
public class HomeController {

  private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

  @Autowired
  HomeService homeService;

  @Autowired
  AssignmentService assignmentService;

  @Autowired
  AssignmentConverter assignmentConverter;

  @Autowired
  ReturnRequestConverter returnRequestConverter;

  @Autowired
  ReturnRequestService returnRequestService;

  @GetMapping("/user/{code}")
  public ResponseEntity<ResponseDTO> viewUserAssignment(@PathVariable("code") String userCode,
                                                        @RequestParam("sort") String sortValue,
                                                        @RequestParam("direction") String direction) throws UserNotFoundException {
    ResponseDTO responseDTO = new ResponseDTO();
    try {
      List<Assignment> assignments = homeService.getListAssignmentOfUser(userCode, direction, sortValue);
      List<AssignmentDTO> responseList = assignmentConverter.convertToDTOList(assignments);
      responseDTO.setData(responseList);
      responseDTO.setSuccessCode(SuccessCode.ASSIGNMENT_LIST_LOADED_SUCCESS);
    } catch (Exception ex) {
      throw new UserNotFoundException(ex.getMessage());
    }
    return ResponseEntity.ok().body(responseDTO);
  }

  @GetMapping("/assignment/{id}")
  public ResponseEntity<ResponseDTO> getDetailsAssignment(@PathVariable("id") Long id) throws DataNotFoundException {
    ResponseDTO response = new ResponseDTO();
    try {
      ViewDetailAssignmentDTO assigmentDTO = assignmentService.getAssignmentDetailToShow(id);
      if (Objects.nonNull(assigmentDTO)) {
        response.setData(assigmentDTO);
        response.setSuccessCode(SuccessCode.ASSIGNMENT_LOADED_SUCCESS);
      } else {
        response.setData(false);
        response.setErrorCode(ErrorCode.ERR_ASSIGNMENT_LOADED_FAIL);
      }
    } catch (Exception ex) {
      LOGGER.info("Having error when loading list AssignmentDetail: " + ex.getMessage());
      response.setErrorCode(ErrorCode.ERR_ASSIGNMENT_LOADED_FAIL);
      throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_LOADED_FAIL);
    }
    return ResponseEntity.ok().body(response);
  }

  @PutMapping("/assignment/accept/{id}")
  public ResponseEntity<ResponseDTO> acceptAssignment(@PathVariable("id") Long id) throws DataNotFoundException {
    ResponseDTO response = new ResponseDTO();
    try {
      boolean result = homeService.acceptAssignment(id);
      response.setData(result);
      if (result) {
        response.setSuccessCode(SuccessCode.ASSIGNMENT_ACCEPT_SUCCESS);
      } else {
        response.setErrorCode(ErrorCode.ERR_ACCEPT_ASSIGNMENT_FAIL);
      }
    } catch (Exception ex) {
      LOGGER.info("Having error when accept assignment: " + ex.getMessage());
      response.setErrorCode(ex.getMessage());
      throw new DataNotFoundException(ErrorCode.ERR_ACCEPT_ASSIGNMENT_FAIL);
    }
    return ResponseEntity.ok().body(response);
  }

  @PutMapping("/assignment/decline/{id}")
  public ResponseEntity<ResponseDTO> declineAssignment(@PathVariable("id") Long id) throws DataNotFoundException {
    ResponseDTO response = new ResponseDTO();
    try {
      boolean result = homeService.declinedAssignment(id);
      response.setData(result);
      if (result) {
        response.setSuccessCode(SuccessCode.ASSIGNMENT_DECLINE_SUCCESS);
      } else {
        response.setErrorCode(ErrorCode.ERR_DECLINE_ASSIGNMENT_FAIL);
      }
    } catch (Exception ex) {
      LOGGER.info("Having error when accept assignment: " + ex.getMessage());
      response.setErrorCode(ex.getMessage());
      throw new DataNotFoundException(ErrorCode.ERR_DECLINE_ASSIGNMENT_FAIL);
    }
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/request")
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
