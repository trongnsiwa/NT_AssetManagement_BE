package com.nashtech.rootkies.service.impl;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.model.Asset;
import com.nashtech.rootkies.model.Assignment;
import com.nashtech.rootkies.model.HistoricalAssignment;
import com.nashtech.rootkies.model.ReturnRequest;
import com.nashtech.rootkies.repository.AssetRepository;
import com.nashtech.rootkies.repository.HistoricalAssignmentRepository;
import com.nashtech.rootkies.service.HistoricalAssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HistoricalAssignmentServiceImpl implements HistoricalAssignmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricalAssignmentServiceImpl.class);
    @Autowired
    AssetRepository assetRepository;

    @Autowired
    HistoricalAssignmentRepository historicalAssignmentRepository;

    @Override
    public boolean checkAssetInHistory(Long assetID) throws DataNotFoundException {
        try {
            Asset asset;
            Optional<Asset> optionalAsset = assetRepository.findById(assetID);
            if (optionalAsset.isPresent()) {
                asset = optionalAsset.get();
            } else {
                throw new DataNotFoundException(ErrorCode.ERR_ASSET_NOT_FOUND);
            }
            List<HistoricalAssignment> historicalAssignments = historicalAssignmentRepository.findByAsset(asset);
            if (!CollectionUtils.isEmpty(historicalAssignments)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_NOT_FOUND);
        }
    }

    @Override
    public HistoricalAssignment createWhenReqAccept(ReturnRequest returnRequest) throws DataNotFoundException {
        try{
            Assignment assignment = returnRequest.getAssignment();
            if(!Objects.nonNull(assignment)){
                LOGGER.info("Assignment {} not found", assignment.getId());
                throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_NOT_FOUND);
            }
            if(!Objects.nonNull(returnRequest)){
                LOGGER.info("ReturnRequest {} not found", returnRequest.getId());
                throw new DataNotFoundException(ErrorCode.ERR_RETURN_REQUEST_NOT_FOUND);
            }
            HistoricalAssignment historicalAssignment= new HistoricalAssignment();
            historicalAssignment.setAsset(assignment.getAsset());
            historicalAssignment.setAssignedBy(assignment.getAssignedBy());
            historicalAssignment.setAssignedTo(assignment.getAssignedTo());
            historicalAssignment.setAssignedDate(assignment.getAssignedDate());
            historicalAssignment.setReturnedDate(returnRequest.getReturnedDate());

            return historicalAssignmentRepository.save(historicalAssignment);

        }catch (DataNotFoundException e) {
            String message = e.getMessage();
            if (message.equals(ErrorCode.ERR_ASSIGNMENT_NOT_FOUND)) {
                throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_NOT_FOUND);
            }else {
                throw new DataNotFoundException(ErrorCode.ERR_RETURN_REQUEST_NOT_FOUND);
            }
        } catch (Exception e){
            throw  new DataNotFoundException(ErrorCode.ERR_HISTORICAL_ASSIGNMENT_CREATE_FAIL);
        }
    }

}
