package com.nashtech.rootkies.service.impl;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.converter.AssetStateConverter;
import com.nashtech.rootkies.dto.state.AssetStateDTO;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.model.AssetState;
import com.nashtech.rootkies.repository.AssetStateRepository;
import com.nashtech.rootkies.service.AssetStateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetStateServiceImpl implements AssetStateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetStateServiceImpl.class);

    @Autowired
    AssetStateRepository assetStateRepository;

    @Autowired
    AssetStateConverter assetStateConverter;

    @Override
    public List<AssetStateDTO> getListAllStates() throws DataNotFoundException {
        List<AssetStateDTO> listDTO;

        try {
            List<AssetState> listEntity = assetStateRepository.findAll();
            listDTO = assetStateConverter.toDTOList(listEntity);
        } catch (Exception ex) {
            LOGGER.info("Having error when loading the asset state list: " + ex.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_ASSET_STATE_LIST_LOADED_FAIL);
        }

        return listDTO;
    }

}
