package com.nashtech.rootkies.service.impl;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.converter.AssetConverter;
import com.nashtech.rootkies.converter.HistoricalAssigmentConverter;
import com.nashtech.rootkies.dto.HistoricalAssigmentDTO.HistoryDTO;
import com.nashtech.rootkies.dto.asset.CreateAssetDTO;
import com.nashtech.rootkies.dto.asset.UpdateAssetDTO;
import com.nashtech.rootkies.dto.asset.ViewAssetDTO;
import com.nashtech.rootkies.dto.asset.ViewDetailAssetDTO;
//import com.nashtech.rootkies.dto.report.ReportDTO;
import com.nashtech.rootkies.dto.report.ReportDTO;
import com.nashtech.rootkies.enums.EAssetState;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.DeleteDataFailException;
import com.nashtech.rootkies.exception.UpdateDataFailException;
import com.nashtech.rootkies.model.Asset;
import com.nashtech.rootkies.model.AssetState;
import com.nashtech.rootkies.model.Category;
import com.nashtech.rootkies.model.HistoricalAssignment;
import com.nashtech.rootkies.model.Location;
import com.nashtech.rootkies.repository.AssetRepository;
import com.nashtech.rootkies.repository.AssetStateRepository;
import com.nashtech.rootkies.repository.CategoryRepository;
import com.nashtech.rootkies.repository.HistoricalAssignmentRepository;
import com.nashtech.rootkies.repository.LocationRepository;
import com.nashtech.rootkies.service.AssetService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.nashtech.rootkies.repository.specs.AssetSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class AssetServiceImpl implements AssetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetServiceImpl.class);

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    AssetConverter assetConverter;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AssetStateRepository assetStateRepository;

    @Autowired
    HistoricalAssigmentConverter historicalAssigmentConverter;

    @Autowired
    HistoricalAssignmentRepository historicalAssignmentRepository;

    @Override
    public int disableAsset(Long id) throws DataNotFoundException, DeleteDataFailException {
        Optional<Asset> optionalUser = assetRepository.findById(id);
        Asset asset;
        if (!optionalUser.isPresent()) {
            throw new DataNotFoundException(ErrorCode.ERR_ASSET_NOT_FOUND);
        } else {
            asset = optionalUser.get();
        }
        int result = 0;
        boolean history = false;
        List<HistoricalAssignment> list = historicalAssignmentRepository.findByAsset(asset);
        if (list.size() > 0) {
            history = true;
        }
        if (asset.getState().getName().toString().equals("AVAILABLE") && history) {
            result = 0;
        } else if (asset.getState().getName().toString().equals("NOT_AVAILABLE") && history) {
            result = 0;
        } else {
            result = assetRepository.disableAsset(id);
        }
        return result;
    }

    @Override
    public List<ViewAssetDTO> getListAllAssetForAdmin(int pageNo, int pageSize, String valueSort,
                                                      String direction, Long locationID) throws DataNotFoundException {
        List<ViewAssetDTO> listAssetDTOS;

        try {
            Optional<Location> tempLocation = locationRepository.findById(locationID);
            Location location;

            if (tempLocation.isPresent()) {
                location = tempLocation.get();
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_LOCATION_NOT_FOUND);
            }

            List<Sort.Order> orders = new ArrayList<>();

            if (StringUtils.isNotEmpty(valueSort)) {
                orders.add(new Sort.Order("ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC, valueSort));
            } else {
                orders.add(new Sort.Order(Sort.Direction.ASC, "name"));
            }

            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(orders));

            Page<Asset> page = assetRepository.findAll(where(isNotDeleted())
                    .and(hasLocation(location)), pageable);

            listAssetDTOS = assetConverter.toDTOList(page.getContent());
        } catch (Exception e) {
            throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
        }

        return listAssetDTOS;
    }

    @Override
    public ViewDetailAssetDTO getAssetByCode(Long id) throws DataNotFoundException {
        try {
            Optional<Asset> optionalAsset = assetRepository.findById(id);
            if (!optionalAsset.isPresent()) {
                throw new DataNotFoundException(ErrorCode.ERR_ASSET_NOT_FOUND);
            }
            Asset assetEntity = optionalAsset.get();
            List<HistoryDTO> listHistory = historicalAssigmentConverter.convertToListHistoryDTO(assetEntity.getHistoricalAssignments());
            ViewDetailAssetDTO detail = assetConverter.convertToViewDetailsDTO(assetEntity);
            detail.setHistory(listHistory);
            return detail;
        } catch (Exception e) {
            throw new DataNotFoundException(ErrorCode.ERR_ASSET_NOT_FOUND);
        }
    }

    @Override
    public int getRecordAllAsset(Long locationID) throws DataNotFoundException {
        try {
            Optional<Location> tempLocation = locationRepository.findById(locationID);
            Location location;

            if (tempLocation.isPresent()) {
                location = tempLocation.get();
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_LOCATION_NOT_FOUND);
            }

            return (int) assetRepository.count(where(isNotDeleted()).and(hasLocation(location)));

        } catch (Exception e) {
            throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
        }
    }

    @Override
    public List<ViewAssetDTO> searchAsset(int pageNo, int pageSize, String valueSort, String direction, Long locationID,
                                          String keyword, List<EAssetState> states, List<String> categories) throws DataNotFoundException {
        List<ViewAssetDTO> listAssetDTOS;

        try {
            Optional<Location> tempLocation = locationRepository.findById(locationID);
            Location location;

            if (tempLocation.isPresent()) {
                location = tempLocation.get();
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_LOCATION_NOT_FOUND);
            }

            List<Sort.Order> orders = new ArrayList<>();

            if (StringUtils.isNotEmpty(valueSort)) {
                orders.add(new Sort.Order("ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC, valueSort));
            } else {
                orders.add(new Sort.Order(Sort.Direction.ASC, "name"));
            }

            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(orders));

            Page<Asset> page = assetRepository.findAll(filter(states, categories, keyword, location), pageable);

            listAssetDTOS = assetConverter.toDTOList(page.getContent());
        } catch (Exception e) {
            throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
        }

        return listAssetDTOS;
    }

    @Override
    public int countSearchAsset(Long locationID, String keyword, List<EAssetState> states, List<String> categories) throws DataNotFoundException {
        try {
            Optional<Location> tempLocation = locationRepository.findById(locationID);
            Location location;

            if (tempLocation.isPresent()) {
                location = tempLocation.get();
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_LOCATION_NOT_FOUND);
            }

            return (int) assetRepository.count(filter(states, categories, keyword, location));
        } catch (Exception e) {
            throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
        }
    }

    @Override
    public Asset createNewAsset(CreateAssetDTO dto) throws CreateDataFailException {
        Asset asset;

        try {
            asset = assetConverter.convertToEntityCreate(dto);

            return assetRepository.save(asset);
        } catch (Exception ex) {
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_ASSET_FAIL);
        }
    }

    public Specification<Asset> filter(List<EAssetState> states, List<String> categories, String keyword, Location location) {
        Specification<Asset> conditions = where(isNotDeleted())
                .and(hasLocation(location));

        if (!CollectionUtils.isEmpty(categories)) {
            boolean first = true;

            Specification<Asset> cateSpec = null;
            for (String category : categories) {
                if (first) {
                    cateSpec = categoryHasName(category);
                    first = false;
                } else {
                    cateSpec = cateSpec.or(categoryHasName(category));
                }
            }
            conditions = conditions.and(cateSpec);
        }

        if (!CollectionUtils.isEmpty(states)) {
            boolean first = true;

            Specification<Asset> stateSpec = null;
            for (EAssetState state : states) {
                if (first) {
                    stateSpec = stateHasName(state);
                    first = false;
                } else {
                    stateSpec = stateSpec.or(stateHasName(state));
                }
                stateSpec = stateSpec.or(stateHasName(state));
            }
            conditions = conditions.and(stateSpec);
        }

        if (StringUtils.isNotEmpty(keyword)) {
            conditions = conditions.and(nameContainsIgnoreCase(keyword));
        }

        return conditions;
    }

    @Override
    public Asset updateAsset(UpdateAssetDTO updateAssetDTO, Long id) throws UpdateDataFailException,
            DataNotFoundException {
        try {
            Optional<Asset> existedAsset = assetRepository.findById(id);
            if (!existedAsset.isPresent()) {
                LOGGER.info("Asset {} not found", id);
                throw new DataNotFoundException(ErrorCode.ERR_ASSET_NOT_FOUND);
            }

            Optional<AssetState> existedAssetState = assetStateRepository.findById(Long.valueOf(updateAssetDTO.getStateId()));
            if (!existedAssetState.isPresent()) {
                LOGGER.info("AssetState {} not found", updateAssetDTO.getStateId());
                throw new DataNotFoundException(ErrorCode.ERR_ASSET_STATE_NOT_FOUND);
            }

            Asset asset = existedAsset.get();
            AssetState tempAssetState = existedAssetState.get();

            asset.setState(tempAssetState);
            asset.setName(updateAssetDTO.getName());
            asset.setSpecification(updateAssetDTO.getSpecification());
            asset.setInstalledDate(updateAssetDTO.getInstalledDate());

            return assetRepository.save(asset);
        } catch (DataNotFoundException e) {
            String message = e.getMessage();

            if (message.equals(ErrorCode.ERR_ASSET_STATE_NOT_FOUND)) {
                throw new DataNotFoundException(ErrorCode.ERR_ASSET_STATE_NOT_FOUND);
            } else if (message.equals(ErrorCode.ERR_CATEGORY_NOT_FOUND)) {
                throw new DataNotFoundException(ErrorCode.ERR_CATEGORY_NOT_FOUND);
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_ASSET_NOT_FOUND);
            }

        } catch (Exception e) {
            LOGGER.info("Fail to update Asset {}", id);
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_ASSET_FAIL);

        }
    }

    @Override
    public List<ReportDTO> getReport(Long locationId) throws DataNotFoundException {
        List<ReportDTO> listReport;
        try {
            List<Category> listCategory = categoryRepository.findAll();
            if (listCategory.size() > 0) {
                listReport = new ArrayList<>();
                for (int i = 1; i < listCategory.size() + 1; i++) {
                    int total = 0;
                    int assigned = 0;
                    int available = 0;
                    int notAvailable = 0;
                    int waitingForRecycling = 0;
                    int waitingForAssign = 0;
                    int recyled = 0;
                    Long id = Long.valueOf(String.valueOf(i));
                    Category category = null;
                    Optional<Category> optionalCategory = categoryRepository.findById(id);
                    if (optionalCategory.isPresent()) {
                        category = optionalCategory.get();
                    } else {
                        throw new DataNotFoundException(ErrorCode.ERR_CATEGORY_NOT_FOUND);
                    }
                    total = assetRepository.getAmountAssetByCategory(category.getId().intValue(), locationId);
                    assigned = assetRepository.getAmountAssetByCategoryAndStateIsAssigned(category.getId().intValue(), locationId);
                    available = assetRepository.getAmountAssetByCategoryAndStateIsAvailable(category.getId().intValue(), locationId);
                    notAvailable = assetRepository.getAmountAssetByCategoryAndStateIsNotAvailable(category.getId().intValue(), locationId);
                    waitingForRecycling = assetRepository.getAmountAssetByCategoryAndStateIsWaitingForRecycling(category.getId().intValue(), locationId);
                    recyled = assetRepository.getAmountAssetByCategoryAndStateIsRecycled(category.getId().intValue(), locationId);
                    waitingForAssign = assetRepository.getAmountAssetByCategoryAndStateIsWaitingForAssign(category.getId().intValue(), locationId);
                    ReportDTO dto = new ReportDTO();
                    dto.setNameCategory(category.getName());
                    dto.setTotal(total);
                    dto.setAmmountAssigned(assigned);
                    dto.setAmmountAvailable(available);
                    dto.setAmmountNotAvailable(notAvailable);
                    dto.setAmmountWaitingForRecycling(waitingForRecycling);
                    dto.setAmmountWaitingForAssign(waitingForAssign);
                    dto.setAmmountRecycled(recyled);
                    listReport.add(dto);
                }
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_CATEGORY_LIST_LOADED_FAIL);
            }
        } catch (Exception ex) {
            LOGGER.info("Having problem when get the report: " + ex);
            throw new DataNotFoundException(ErrorCode.ERR_REPORT_LOADED_FAIL);
        }
        return listReport;
    }

}