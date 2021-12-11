package com.nashtech.rootkies.converter;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.dto.state.AssetStateDTO;
import com.nashtech.rootkies.exception.ConvertEntityDTOException;
import com.nashtech.rootkies.model.AssetState;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssetStateConverter {

    @Autowired
    ModelMapper modelMapper;

    public AssetStateDTO convertToDTO(AssetState assetState) {
        AssetStateDTO dto = modelMapper.map(assetState, AssetStateDTO.class);
        dto.setName(assetState.getName().toString());

        return dto;
    }

    public List<AssetStateDTO> toDTOList(List<AssetState> listEntity) throws ConvertEntityDTOException {
        List<AssetStateDTO> listDTO;

        try {
            listDTO = listEntity.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

        return listDTO;
    }

}
