package com.nashtech.rootkies.converter;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.dto.returnrequest.ViewReturnRequestDTO;
import com.nashtech.rootkies.exception.ConvertEntityDTOException;
import com.nashtech.rootkies.model.ReturnRequest;
import com.nashtech.rootkies.repository.AssignmentRepository;
import com.nashtech.rootkies.repository.ReturnRequestRepository;
import com.nashtech.rootkies.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ReturnRequestConverter {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ReturnRequestRepository returnRequestRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    UserRepository userRepository;

    public ViewReturnRequestDTO convertToDTO(ReturnRequest returnRequest){
            ViewReturnRequestDTO dto = modelMapper.map(returnRequest, ViewReturnRequestDTO.class);
            dto.setId(returnRequest.getId());
            dto.setAssetCode(returnRequest.getAsset().getCode());
            dto.setAssetName(returnRequest.getAsset().getName());
            dto.setRequestBy(returnRequest.getRequestBy().getUsername());
            dto.setAssignedDate(returnRequest.getAssignedDate());
            dto.setState(returnRequest.getState());
            if(Objects.isNull(returnRequest.getAcceptedBy())){
                dto.setAcceptedBy(null);
            }else{
                dto.setAcceptedBy(returnRequest.getAcceptedBy().getUsername());
            }

            if(returnRequest.getReturnedDate() == null){
                dto.setReturnedDate(null);
            }else{
                dto.setReturnedDate(returnRequest.getReturnedDate());
            }
            return dto;
    }

    public List<ViewReturnRequestDTO> convertToListDTO(List<ReturnRequest> listEntity) throws ConvertEntityDTOException{
        List<ViewReturnRequestDTO> listDTO;
        try {
            listDTO = listEntity.stream().map(this::convertToDTO).collect(Collectors.toList());
        }catch(Exception e){
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERT_DTO_ENTITY_FAIL);
        }
        return listDTO;
    }


}
