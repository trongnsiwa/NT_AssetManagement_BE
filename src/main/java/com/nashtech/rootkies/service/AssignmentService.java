package com.nashtech.rootkies.service;

import com.nashtech.rootkies.dto.assigment.ViewAssignmentDTO;
import com.nashtech.rootkies.dto.assigment.ViewDetailAssignmentDTO;
import com.nashtech.rootkies.dto.user.ViewDetailsUserDTO;
import com.nashtech.rootkies.dto.user.ViewUserDTO;
import com.nashtech.rootkies.enums.EAssignmentState;
import com.nashtech.rootkies.dto.assigment.UpdateAssigmentDTO;
import com.nashtech.rootkies.dto.assigment.CreateAssignmentDTO;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.DeleteDataFailException;
import com.nashtech.rootkies.exception.UpdateDataFailException;
import com.nashtech.rootkies.exception.UserNotFoundException;
import com.nashtech.rootkies.model.Assignment;

import java.time.LocalDateTime;
import java.util.List;

public interface AssignmentService {

  List<ViewAssignmentDTO> getListAllAssignment(int pageNo, int pageSize, String direction, String valueSort) throws DataNotFoundException;

  List<ViewAssignmentDTO> getListSearchAndFilterAssignment(int pageNo, int pageSize, String direction, String valueSort, String searchBy, List<EAssignmentState> assignmentState, LocalDateTime assignedDate) throws DataNotFoundException;

  int getRecordListAllAssignment() throws DataNotFoundException;

  int getRecordListSearchAndFilterAssignment(String searchBy,List<EAssignmentState> assignmentState, LocalDateTime assignedDate) throws DataNotFoundException;

  ViewDetailAssignmentDTO getAssignmentDetailToShow(Long id) throws DataNotFoundException;

  LocalDateTime getAssignDateLastest() throws DataNotFoundException;

  boolean checkIfValidAssignment(String staffCode) throws UserNotFoundException, DataNotFoundException;

  Assignment updateAssigment(UpdateAssigmentDTO assignmentDTO, Long id) throws UpdateDataFailException, DataNotFoundException, UserNotFoundException;

  Assignment createAssignment(CreateAssignmentDTO createAssignmentDTO)
          throws CreateDataFailException, UserNotFoundException, DataNotFoundException;

  int deleteAssignment(Long id) throws DeleteDataFailException, DataNotFoundException;

}
