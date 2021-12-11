package com.nashtech.rootkies.service;

import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.UserNotFoundException;
import com.nashtech.rootkies.model.Assignment;

import java.util.List;

public interface HomeService {

  List<Assignment> getListAssignmentOfUser(String userCode, String direction, String valueSort) throws UserNotFoundException;

  boolean acceptAssignment(Long assignmentId) throws DataNotFoundException;

  boolean declinedAssignment(Long assignmentId) throws DataNotFoundException;

}
