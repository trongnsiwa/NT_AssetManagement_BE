package com.nashtech.rootkies.service.impl;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.converter.AssignmentStateConverter;
import com.nashtech.rootkies.dto.state.AssignmentStateDTO;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.model.AssignmentState;
import com.nashtech.rootkies.repository.AssignmentStateRepository;
import com.nashtech.rootkies.service.AssignmentStateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentStateServiceImpl implements AssignmentStateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentStateServiceImpl.class);

    @Autowired
    AssignmentStateRepository assignmentStateRepository;

    @Autowired
    AssignmentStateConverter assignmentStateConverter;

    @Override
    public List<AssignmentStateDTO> getListAllAssignments() throws DataNotFoundException {
        List<AssignmentStateDTO> listDTO;

        try {
            List<AssignmentState> listEntity = assignmentStateRepository.findAll();
            listDTO = assignmentStateConverter.toDTOList(listEntity);
        } catch (Exception ex) {
            LOGGER.info("Having error when loading the assignment state list: " + ex.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_STATE_LIST_LOADED_FAIL);
        }

        return listDTO;
    }

}
