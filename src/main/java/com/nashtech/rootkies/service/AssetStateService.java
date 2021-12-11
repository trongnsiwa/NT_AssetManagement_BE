package com.nashtech.rootkies.service;

import com.nashtech.rootkies.dto.state.AssetStateDTO;
import com.nashtech.rootkies.exception.DataNotFoundException;

import java.util.List;

public interface AssetStateService {

    List<AssetStateDTO> getListAllStates() throws DataNotFoundException;

}
