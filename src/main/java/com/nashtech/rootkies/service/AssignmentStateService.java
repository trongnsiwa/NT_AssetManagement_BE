package com.nashtech.rootkies.service;

import com.nashtech.rootkies.dto.state.AssignmentStateDTO;
import com.nashtech.rootkies.exception.DataNotFoundException;

import java.util.List;

public interface AssignmentStateService {

    List<AssignmentStateDTO> getListAllAssignments() throws DataNotFoundException;

}
