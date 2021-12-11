package com.nashtech.rootkies.converter;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.exception.ConvertEntityDTOException;
import com.nashtech.rootkies.model.HistoricalAssignment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import com.nashtech.rootkies.dto.HistoricalAssigmentDTO.HistoryDTO;

@Component
public class HistoricalAssigmentConverter {

    @Autowired
    ModelMapper modelMapper;

    public HistoryDTO convertToHistoryDTO (HistoricalAssignment history){
        HistoryDTO historyDTO = modelMapper.map(history, HistoryDTO.class);
        historyDTO.setAssignedBy(history.getAssignedBy().getUsername());
        historyDTO.setAssignedTo(history.getAssignedTo().getUsername());
        return historyDTO;
    }

    public List<HistoryDTO> convertToListHistoryDTO(List<HistoricalAssignment> histories) throws ConvertEntityDTOException {
        List<HistoryDTO> historyDTO;
        try {
            historyDTO = histories.stream().map(this::convertToHistoryDTO).collect(Collectors.toList());
        }catch(Exception e){
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERT_DTO_ENTITY_FAIL);
        }
        return historyDTO;
    }
}
