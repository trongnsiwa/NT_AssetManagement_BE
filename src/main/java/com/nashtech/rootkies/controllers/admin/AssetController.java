package com.nashtech.rootkies.controllers.admin;

import com.google.common.base.Strings;
import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.constants.SuccessCode;
import com.nashtech.rootkies.converter.AssetConverter;
import com.nashtech.rootkies.dto.asset.CreateAssetDTO;
import com.nashtech.rootkies.dto.asset.UpdateAssetDTO;
import com.nashtech.rootkies.dto.asset.ViewAssetDTO;
import com.nashtech.rootkies.dto.asset.ViewDetailAssetDTO;
import com.nashtech.rootkies.dto.common.ResponseDTO;
import com.nashtech.rootkies.dto.report.ReportDTO;
import com.nashtech.rootkies.dto.state.AssetStateDTO;
import com.nashtech.rootkies.enums.EAssetState;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.DeleteDataFailException;
import com.nashtech.rootkies.exception.UpdateDataFailException;
import com.nashtech.rootkies.model.Asset;
import com.nashtech.rootkies.model.HistoricalAssignment;
import com.nashtech.rootkies.service.AssetService;
import com.nashtech.rootkies.service.AssetStateService;

import com.nashtech.rootkies.service.HistoricalAssignmentService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/asset")
@Api(tags = "Asset")
public class AssetController {

    @Autowired
    AssetStateService assetStateService;

    @Autowired
    HistoricalAssignmentService historicalAssignmentService;

    @Autowired
    AssetService assetService;

    @Autowired
    AssetConverter assetConverter;

    @Autowired
    ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetController.class);

    @ResponseBody
    @GetMapping("/list-state")
    public ResponseEntity<ResponseDTO> getAssetStateList() throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();
        try {
            List<AssetStateDTO> listDTO = assetStateService.getListAllStates();
            response.setSuccessCode(SuccessCode.ASSET_STATE_LIST_LOADED_SUCCESS);
            response.setData(listDTO);
        } catch (Exception ex) {
            throw new DataNotFoundException(ErrorCode.ERR_ASSET_STATE_LIST_LOADED_FAIL);
        }

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteAsset(@PathVariable("id") Long id) throws DeleteDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            int value = assetService.disableAsset(id);
            if (value > 0) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.ASSET_DELETE_SUCCESS);
            } else {
                response.setData(true);
                response.setErrorCode(ErrorCode.ERR_DELETE_ASSET_FAIL);
            }
        } catch (Exception ex) {
            throw new DeleteDataFailException(ex.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/check-delete/{id}")
    public ResponseEntity<ResponseDTO> checkForDeleteAsset(@PathVariable("id") Long id) throws DeleteDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result = historicalAssignmentService.checkAssetInHistory(id);
            response.setData(result);
            if (result) {
                response.setErrorCode(ErrorCode.ERR_ASSET_EXISTS_IN_HISTORY_ASSIGNMENT);
            } else {
                response.setSuccessCode(SuccessCode.ASSET_DELETE_SUCCESS);
            }
        } catch (Exception ex) {
            throw new DeleteDataFailException(ex.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getList")
    public ResponseEntity<ResponseDTO> getListAsset(
            @RequestParam("page") @Min(1) Integer page,
            @RequestParam("size") @Min(1) Integer size,
            @RequestParam("sort") String sortValue,
            @RequestParam("direction") String direction,
            @RequestParam("location") @Min(1) Integer location
    ) throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();
        try {
            Long locationID = Long.valueOf(String.valueOf(location));
            List<ViewAssetDTO> listDTO = assetService.getListAllAssetForAdmin(page, size, sortValue.trim(), direction.trim(), locationID);
            if (listDTO.size() > 0) {
                response.setData(listDTO);
                response.setSuccessCode(SuccessCode.ASSET_LIST_LOADED_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_ASSET_LIST_LOADED_FAIL);
            }
        } catch (Exception ex) {
            LOGGER.info("Having error when loading list Asset: " + ex.getMessage());
            response.setErrorCode(ErrorCode.ERR_ASSET_LIST_LOADED_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_ASSET_LIST_LOADED_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    private List<String> getListCategory(String categories) {
        if (Strings.isNullOrEmpty(categories)) {
            return Collections.emptyList();
        }

        return Arrays.asList(categories.split(",")).stream().map(String::trim).collect(Collectors.toList());
    }

    private List<EAssetState> getListState(String states) {
        if (Strings.isNullOrEmpty(states)) {
            return Collections.emptyList();
        }

        return Arrays.asList(states.split(",")).stream().map(String::trim)
                .map(EAssetState::valueOf).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchAsset(
            @RequestParam("page") @Min(1) Integer page,
            @RequestParam("size") @Min(1) Integer size,
            @RequestParam("sort") String sortValue,
            @RequestParam("direction") String direction,
            @RequestParam("location") @Min(1) Integer location,
            @RequestParam("keyword") String keyword,
            @RequestParam("states") String states,
            @RequestParam("categories") String categories
    ) throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();
        try {
            Long locationID = Long.valueOf(String.valueOf(location));
            List<ViewAssetDTO> listDTO = assetService.searchAsset(page, size, sortValue.trim(), direction.trim(),
                    locationID,
                    keyword.trim(),
                    getListState(states.trim()), getListCategory(categories.trim()));
            if (listDTO.size() > 0) {
                response.setData(listDTO);
                response.setSuccessCode(SuccessCode.ASSET_LIST_LOADED_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_ASSET_LIST_LOADED_FAIL);
            }
        } catch (Exception ex) {
            LOGGER.info("Having error when loading list Asset: " + ex.getMessage());
            response.setErrorCode(ErrorCode.ERR_ASSET_LIST_LOADED_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_ASSET_LIST_LOADED_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/recordSearch")
    public ResponseEntity<ResponseDTO> countSearchAsset(
            @RequestParam("location") @Min(1) Integer location,
            @RequestParam("keyword") String keyword,
            @RequestParam("states") String states,
            @RequestParam("categories") String categories
    ) throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();
        try {
            Long locationID = Long.valueOf(String.valueOf(location));
            int count = assetService.countSearchAsset(locationID, keyword, getListState(states), getListCategory(categories));
            response.setData(count);
            response.setSuccessCode(SuccessCode.ASSET_LIST_LOADED_SUCCESS);
        } catch (Exception ex) {
            LOGGER.info("Having error when loading list Asset: " + ex.getMessage());
            response.setErrorCode(ErrorCode.ERR_ASSET_LIST_LOADED_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_ASSET_LIST_LOADED_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getDetailsAsset(@PathVariable("id") Long id) throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();
        try {
            ViewDetailAssetDTO assetDTO = assetService.getAssetByCode(id);
            if (Objects.nonNull(assetDTO)) {
                response.setData(assetDTO);
                response.setSuccessCode(SuccessCode.ASSET_LOADED_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_ASSET_LOADED_FAIL);
            }
        } catch (Exception ex) {
            LOGGER.info("Having error when loading list Asset: " + ex.getMessage());
            response.setErrorCode(ErrorCode.ERR_ASSET_LOADED_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_ASSET_LOADED_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/record")
    public ResponseEntity<ResponseDTO> getListAssetCount(@RequestParam("location") @Min(1) Integer location) throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();
        try {
            Long locationID = Long.valueOf(String.valueOf(location));
            int value = assetService.getRecordAllAsset(locationID);
            if (value > 0) {
                response.setData(value);
                response.setSuccessCode(SuccessCode.ASSET_LIST_LOADED_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_ASSET_LIST_LOADED_FAIL);
            }
        } catch (Exception ex) {
            LOGGER.info("Having error when loading list Asset: " + ex.getMessage());
            response.setErrorCode(ErrorCode.ERR_ASSET_LIST_LOADED_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_ASSET_LIST_LOADED_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createAsset(@Valid @RequestBody CreateAssetDTO dto)
            throws CreateDataFailException {
        ResponseDTO response = new ResponseDTO();

        try {
            Asset asset = assetService.createNewAsset(dto);
            ViewAssetDTO createAssetDTO = assetConverter.convertToViewDTO(asset);

            response.setData(createAssetDTO);
            response.setSuccessCode(SuccessCode.ASSET_CREATE_SUCCESS);
        } catch (Exception ex) {
            response.setErrorCode(ErrorCode.ERR_CREATE_ASSET_FAIL);
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_ASSET_FAIL);
        }

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateAsset(
            @Valid @RequestBody UpdateAssetDTO updateAssetDTO)
            throws UpdateDataFailException, DataNotFoundException {
        ResponseDTO response = new ResponseDTO();
        try {
            Asset updateAsset =
                    assetService.updateAsset(updateAssetDTO, updateAssetDTO.getId());
            ViewAssetDTO responseProduct = assetConverter.convertToViewDTO(updateAsset);
            response.setData(responseProduct);
            response.setSuccessCode(SuccessCode.ASSET_UPDATE_SUCCESS);
        } catch (DataNotFoundException e) {
            String message = e.getMessage();
            if (message.equals(ErrorCode.ERR_ASSET_STATE_NOT_FOUND)) {
                response.setErrorCode(ErrorCode.ERR_ASSET_STATE_NOT_FOUND);
                throw new DataNotFoundException(ErrorCode.ERR_ASSET_STATE_NOT_FOUND);
            } else if (message.equals(ErrorCode.ERR_CATEGORY_NOT_FOUND)) {
                response.setErrorCode(ErrorCode.ERR_CATEGORY_NOT_FOUND);
                throw new DataNotFoundException(ErrorCode.ERR_CATEGORY_NOT_FOUND);
            } else {
                response.setErrorCode(ErrorCode.ERR_ASSET_NOT_FOUND);
                throw new DataNotFoundException(ErrorCode.ERR_ASSET_NOT_FOUND);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_UPDATE_ASSET_FAIL);
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_ASSET_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/report")
    public ResponseEntity<ResponseDTO> getReport(@RequestParam("location") @Min(1) Integer location) throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();
        try {
            Long locationId = Long.parseLong(String.valueOf(location));
            List<ReportDTO> listReport = assetService.getReport(locationId);
            if (listReport.size() > 0) {
                response.setData(listReport);
                response.setSuccessCode(SuccessCode.REPORT_LOADED_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_REPORT_LOADED_FAIL);
            }
        } catch (Exception ex) {
            response.setErrorCode(ErrorCode.ERR_REPORT_LOADED_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_REPORT_LOADED_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

}
