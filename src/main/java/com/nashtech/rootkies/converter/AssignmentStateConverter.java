package com.nashtech.rootkies.converter;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.dto.state.AssignmentStateDTO;
import com.nashtech.rootkies.exception.ConvertEntityDTOException;
import com.nashtech.rootkies.model.AssignmentState;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssignmentStateConverter {

    @Autowired
    ModelMapper modelMapper;

    public AssignmentStateDTO convertToDTO(AssignmentState assignmentState) {
        AssignmentStateDTO dto = modelMapper.map(assignmentState, AssignmentStateDTO.class);
        dto.setName(assignmentState.getName().toString());
        return dto;
    }

    public List<AssignmentStateDTO> toDTOList(List<AssignmentState> listEntity) throws ConvertEntityDTOException {
        List<AssignmentStateDTO> listDTO;

        try {
            listDTO = listEntity.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

        return listDTO;
    }

}
