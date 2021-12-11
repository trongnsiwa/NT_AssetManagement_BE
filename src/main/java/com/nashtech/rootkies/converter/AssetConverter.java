package com.nashtech.rootkies.converter;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.dto.asset.CreateAssetDTO;
import com.nashtech.rootkies.dto.asset.ViewAssetDTO;
import com.nashtech.rootkies.dto.asset.ViewDetailAssetDTO;
import com.nashtech.rootkies.dto.state.AssetStateDTO;
import com.nashtech.rootkies.exception.ConvertEntityDTOException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.model.*;
import com.nashtech.rootkies.repository.*;
import com.nashtech.rootkies.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AssetConverter {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    AssetStateRepository assetStateRepository;

    @Autowired
    UserRepository userRepository;

    public ViewAssetDTO convertToViewDTO(Asset asset) {
        ViewAssetDTO dto = modelMapper.map(asset, ViewAssetDTO.class);
        dto.setCategoryName(asset.getCategory().getName());

        AssetStateDTO assetStateDTO = modelMapper.map(asset.getState(), AssetStateDTO.class);

        dto.setState(assetStateDTO);
        return dto;
    }

    public List<ViewAssetDTO> toDTOList(List<Asset> listEntity) throws ConvertEntityDTOException {
        List<ViewAssetDTO> listDTO;

        try {
            listDTO = listEntity.stream().map(this::convertToViewDTO).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

        return listDTO;
    }

    public ViewDetailAssetDTO convertToViewDetailsDTO(Asset asset) {
        ViewDetailAssetDTO dto = modelMapper.map(asset, ViewDetailAssetDTO.class);
        dto.setCategoryName(asset.getCategory().getName());
        dto.setLocationName(asset.getLocation().getName());
        dto.setStateName(asset.getState().getName().name());
        return dto;
    }

    public Asset convertToEntityCreate(CreateAssetDTO dto) throws ConvertEntityDTOException {
        try {
            Asset asset = modelMapper.map(dto, Asset.class);

            Category category = categoryRepository.findByName(dto.getCategoryName());
            String tempCode = assetRepository.findMaxAssetCode(category.getPrefix());
            String code;
            if (tempCode == null) {
                code = category.getPrefix() + String.format("%06d", 1);
            } else {
                String numberPart = tempCode.substring(tempCode.indexOf(category.getPrefix()) + 2);

                int numberConverted = Integer.parseInt(numberPart);

                code = category.getPrefix() + String.format("%06d", numberConverted + 1);
            }
            asset.setCode(code);
            asset.setCategory(category);

            Optional<Location> optLoc = locationRepository.findById(dto.getLocationId());
            Location location;
            if (optLoc.isPresent()) {
                location = optLoc.get();
            } else throw new DataNotFoundException(ErrorCode.ERR_LOCATION_NOT_FOUND);
            asset.setLocation(location);

            Optional<User> optUser = userRepository.getUserByCode(dto.getManagedBy());
            User asset_manager;
            if (optUser.isPresent()) {
                asset_manager = optUser.get();
            } else throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
            asset.setManagedBy(asset_manager);

            Optional<AssetState> optState = assetStateRepository.findByName(dto.getState().getName());
            AssetState assetState;
            if (optState.isPresent()) {
                assetState = optState.get();
            } else throw new DataNotFoundException(ErrorCode.ERR_ASSET_STATE_NOT_FOUND);
            asset.setState(assetState);

            asset.setDeleted(false);

            return asset;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERT_DTO_ENTITY_FAIL);
        }
    }
}
