package com.nashtech.rootkies.service;

import com.nashtech.rootkies.dto.asset.CreateAssetDTO;
import com.nashtech.rootkies.dto.asset.UpdateAssetDTO;
import com.nashtech.rootkies.dto.asset.ViewAssetDTO;
import com.nashtech.rootkies.dto.asset.ViewDetailAssetDTO;

import com.nashtech.rootkies.dto.report.ReportDTO;
import com.nashtech.rootkies.enums.EAssetState;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.DeleteDataFailException;
import com.nashtech.rootkies.model.Asset;
import com.nashtech.rootkies.exception.UpdateDataFailException;

import java.util.List;

public interface AssetService {

    int disableAsset(Long id) throws DataNotFoundException, DeleteDataFailException;

    List<ViewAssetDTO> getListAllAssetForAdmin(int pageNo, int pageSize, String valueSort, String direction,
                                               Long locationID) throws DataNotFoundException;

    ViewDetailAssetDTO getAssetByCode(Long id) throws DataNotFoundException;

    int getRecordAllAsset(Long locationID) throws DataNotFoundException;

    List<ViewAssetDTO> searchAsset(int pageNo, int pageSize, String valueSort, String direction, Long locationID,
                                   String name, List<EAssetState> states, List<String> categories) throws DataNotFoundException;

    int countSearchAsset(Long locationID, String name, List<EAssetState> states, List<String> categories) throws DataNotFoundException;

    Asset createNewAsset(CreateAssetDTO dto) throws CreateDataFailException;

    Asset updateAsset(UpdateAssetDTO updateAssetDTO, Long id) throws UpdateDataFailException, DataNotFoundException;

    List<ReportDTO> getReport(Long locationId) throws DataNotFoundException;

}
