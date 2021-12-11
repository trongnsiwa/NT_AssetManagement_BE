package com.nashtech.rootkies.controllers.admin;

import com.google.common.base.Strings;
import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.constants.SuccessCode;
import com.nashtech.rootkies.converter.AssignmentConverter;
import com.nashtech.rootkies.dto.assigment.UpdateAssigmentDTO;
import com.nashtech.rootkies.dto.assigment.ViewAssignmentDTO;
import com.nashtech.rootkies.dto.assigment.ViewDetailAssignmentDTO;
import com.nashtech.rootkies.dto.assigment.CreateAssignmentDTO;
import com.nashtech.rootkies.dto.common.ResponseDTO;
import com.nashtech.rootkies.dto.state.AssignmentStateDTO;
import com.nashtech.rootkies.enums.EAssignmentState;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.DeleteDataFailException;
import com.nashtech.rootkies.exception.UpdateDataFailException;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.UserNotFoundException;
import com.nashtech.rootkies.model.Assignment;
import com.nashtech.rootkies.service.AssignmentService;
import com.nashtech.rootkies.service.AssignmentStateService;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/assignment")
@Api(tags = "Admin - Assignment")
public class AssignmentController {

  @Autowired
  AssignmentService assignmentService;

  @Autowired
  AssignmentStateService assignmentStateService;

  @Autowired
  AssignmentConverter assignmentConverter;

  private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentController.class);

  @GetMapping("/check-valid/{code}")
  public ResponseEntity<ResponseDTO> checkIfValidAssignment(@PathVariable("code") String staffCode) throws UserNotFoundException {
    ResponseDTO response = new ResponseDTO();

    try {
      boolean result = assignmentService.checkIfValidAssignment(staffCode);
      response.setSuccessCode(SuccessCode.USER_LOADED_SUCCESS);
      response.setData(result);
    } catch (Exception ex) {
      response.setErrorCode(ErrorCode.ERR_USER_NOT_FOUND);
      throw new UserNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
    }

    return ResponseEntity.ok().body(response);
  }


  private List<EAssignmentState> getListState(String states) {
    if (Strings.isNullOrEmpty(states)) {
      return Collections.emptyList();
    }

    return Arrays.asList(states.split(",")).stream().map(String::trim)
        .map(EAssignmentState::valueOf).collect(Collectors.toList());
  }

  @ResponseBody
  @GetMapping("/list-state")
  public ResponseEntity<ResponseDTO> getAssignmentStateList() throws DataNotFoundException {
    ResponseDTO response = new ResponseDTO();

    try {
      List<AssignmentStateDTO> listDTO = assignmentStateService.getListAllAssignments();

      response.setSuccessCode(SuccessCode.ASSIGNMENT_STATE_LIST_LOADED_SUCCESS);
      response.setData(listDTO);
    } catch (Exception ex) {
      throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_STATE_LIST_LOADED_FAIL);
    }

    return ResponseEntity.ok().body(response);
  }

    @PutMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteAssignment(@PathVariable(value = "id") Long id)
            throws DeleteDataFailException {
        ResponseDTO response = new ResponseDTO();
        int result;

        try {
            result = assignmentService.deleteAssignment(id);
            response.setData(result);
            response.setSuccessCode(SuccessCode.ASSIGNMENT_DELETE_SUCCESS);
        }catch (Exception ex) {
            response.setErrorCode(ErrorCode.ERR_DELETE_ASSIGNMENT_FAIL);
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_ASSIGNMENT_FAIL);
        }

        return ResponseEntity.ok().body(response);
    }

  @GetMapping("/getList")
  public ResponseEntity<ResponseDTO> getListAssignment(
      @RequestParam("page") @Min(1) Integer page,
      @RequestParam("size") @Min(1) Integer size,
      @RequestParam("sort") String sortValue,
      @RequestParam("direction") String direction
  ) throws DataNotFoundException {
    ResponseDTO response = new ResponseDTO();
    try {
      List<ViewAssignmentDTO> listDTO = assignmentService.getListAllAssignment(page, size, direction.trim(), sortValue.trim());
      if (listDTO.size() > 0) {
        response.setData(listDTO);
        response.setSuccessCode(SuccessCode.ASSIGNMENT_LIST_LOADED_SUCCESS);
      } else {
        response.setData(false);
        response.setErrorCode(ErrorCode.ERR_ASSIGNMENT_LIST_LOADED_FAIL);
      }
    } catch (Exception ex) {
      LOGGER.info("Having error when loading list Assignment: " + ex.getMessage());
      response.setErrorCode(ErrorCode.ERR_ASSIGNMENT_LIST_LOADED_FAIL);
      throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_LIST_LOADED_FAIL);
    }
    return ResponseEntity.ok().body(response);
  }


  @GetMapping("/search")
  public ResponseEntity<ResponseDTO> searchAssignment(
      @RequestParam("page") @Min(1) Integer page,
      @RequestParam("size") @Min(1) Integer size,
      @RequestParam("sort") String sortValue,
      @RequestParam("keyword") String keyword,
      @RequestParam("state") String states,
      @RequestParam("direction") String direction,
      @RequestParam(value = "assignedDate", required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
  ) throws DataNotFoundException {
    ResponseDTO response = new ResponseDTO();
    try {
      List<ViewAssignmentDTO> listDTO = assignmentService.getListSearchAndFilterAssignment(page, size, direction.trim(), sortValue.trim(), keyword.trim(), getListState(states), date);
      if (listDTO.size() > 0) {
        response.setData(listDTO);
        response.setSuccessCode(SuccessCode.ASSIGNMENT_LIST_LOADED_SUCCESS);
      } else {
        response.setData(false);
        response.setErrorCode(ErrorCode.ERR_ASSIGNMENT_LIST_LOADED_FAIL);
      }
    } catch (Exception ex) {
      LOGGER.info("Having error when loading list Assignment: " + ex.getMessage());
      response.setErrorCode(ErrorCode.ERR_ASSIGNMENT_LIST_LOADED_FAIL);
      throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_LIST_LOADED_FAIL);
    }
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/recordSearch")
  public ResponseEntity<ResponseDTO> countSearchAssignment(
      @RequestParam("keyword") String keyword,
      @RequestParam("state") String states,
      @RequestParam(value = "assignedDate", required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
  ) throws DataNotFoundException {
    ResponseDTO response = new ResponseDTO();
    try {
      int count = assignmentService.getRecordListSearchAndFilterAssignment(keyword, getListState(states), date);
      response.setData(count);
      response.setSuccessCode(SuccessCode.ASSIGNMENT_LIST_LOADED_SUCCESS);
    } catch (Exception ex) {
      LOGGER.info("Having error when loading list Assignment: " + ex.getMessage());
      response.setErrorCode(ErrorCode.ERR_ASSIGNMENT_LIST_LOADED_FAIL);
      throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_LIST_LOADED_FAIL);
    }
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{id}")
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

  @GetMapping("/record")
  public ResponseEntity<ResponseDTO> getListAssignmentCount() throws DataNotFoundException {
    ResponseDTO response = new ResponseDTO();
    try {
      int value = assignmentService.getRecordListAllAssignment();
      response.setData(value);
      response.setSuccessCode(SuccessCode.ASSIGNMENT_LIST_LOADED_SUCCESS);
    } catch (Exception ex) {
      LOGGER.info("Having error when loading list Assignment: " + ex.getMessage());
      response.setErrorCode(ErrorCode.ERR_ASSIGNMENT_LIST_LOADED_FAIL);
      throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_LIST_LOADED_FAIL);
    }
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/last_date")
  public ResponseEntity<ResponseDTO> getAssignedDateLast() throws DataNotFoundException {
    ResponseDTO response = new ResponseDTO();
    try {
      LocalDateTime value = assignmentService.getAssignDateLastest();
      if (Objects.nonNull(value)) {
        response.setData(value);
        response.setSuccessCode(SuccessCode.ASSIGNMENT_LIST_LOADED_SUCCESS);
      } else {
        response.setData(false);
        response.setErrorCode(ErrorCode.ERR_ASSIGNMENT_LIST_LOADED_FAIL);
      }
    } catch (Exception ex) {
      LOGGER.info("Having error when loading list Assignment: " + ex.getMessage());
      response.setErrorCode(ErrorCode.ERR_ASSIGNMENT_LIST_LOADED_FAIL);
      throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_LIST_LOADED_FAIL);
    }
    return ResponseEntity.ok().body(response);
  }

  @PutMapping
  public ResponseEntity<ResponseDTO> editAssigment(@Valid @RequestBody UpdateAssigmentDTO updateAssigmentDTO)
      throws UpdateDataFailException, DataNotFoundException, UserNotFoundException {
    ResponseDTO response = new ResponseDTO();
    try {
      Assignment assigmentUpdate = assignmentService.updateAssigment(updateAssigmentDTO, updateAssigmentDTO.getId());
      response.setData(assignmentConverter.convertToAssignmentDTO(assigmentUpdate));
      response.setSuccessCode(SuccessCode.ASSIGNMENT_UPDATE_SUCCESS);
    } catch (DataNotFoundException e){
      throw new DataNotFoundException(e.getMessage());
    } catch (UserNotFoundException e) {
      throw new UserNotFoundException(e.getMessage());
    } catch (Exception e) {
      throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_ASSIGNMENT_FAIL);
    }

    return ResponseEntity.ok().body(response);
  }

  @PostMapping
  public ResponseEntity<ResponseDTO> createAssignment(@RequestBody CreateAssignmentDTO createAssignmentDTO)
      throws CreateDataFailException, DataNotFoundException, UserNotFoundException {
    ResponseDTO responseDTO = new ResponseDTO();
    try {
      Assignment assignment = assignmentService.createAssignment(createAssignmentDTO);
      ViewAssignmentDTO assignmentDTO = assignmentConverter.convertToAssignmentDTO(assignment);
      responseDTO.setData(assignmentDTO);
      responseDTO.setSuccessCode(SuccessCode.ASSIGNMENT_CREATE_SUCCESS);
    } catch (DataNotFoundException e){
      throw new DataNotFoundException(ErrorCode.ERR_ASSET_NOT_FOUND);
    } catch (UserNotFoundException e){
      throw new UserNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
    } catch (Exception e){
      throw new CreateDataFailException(ErrorCode.ERR_CREATE_ASSIGNMENT_FAIL);
    }
    return ResponseEntity.ok().body(responseDTO);
  }
}
