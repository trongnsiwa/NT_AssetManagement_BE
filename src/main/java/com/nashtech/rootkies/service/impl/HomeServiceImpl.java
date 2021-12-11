package com.nashtech.rootkies.service.impl;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.enums.EAssetState;
import com.nashtech.rootkies.enums.EAssignmentState;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.UserNotFoundException;
import com.nashtech.rootkies.model.*;
import com.nashtech.rootkies.repository.*;
import com.nashtech.rootkies.repository.specs.AssignmentSpecification;
import com.nashtech.rootkies.service.HomeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.nashtech.rootkies.repository.specs.AssignmentSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class HomeServiceImpl implements HomeService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  AssignmentRepository assignmentRepository;

  @Autowired
  AssetRepository assetRepository;

  @Autowired
  AssignmentStateRepository assignmentStateRepository;

  @Autowired
  AssetStateRepository assetStateRepository;

  @Override
  public List<Assignment> getListAssignmentOfUser(String userCode, String direction, String valueSort) throws UserNotFoundException {
    User currentUser = userRepository.findByCode(userCode);
    if (Objects.isNull(currentUser)) {
      throw new UserNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
    }

    List<Sort.Order> orders = new ArrayList<>();

    if (StringUtils.isNotEmpty(valueSort)) {
      orders.add(new Sort.Order("ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC, valueSort));
    } else {
      orders.add(new Sort.Order(Sort.Direction.ASC, "assignedDate"));
    }

    return assignmentRepository.findAll(where(isNotDeleted())
        .and(hasUser(currentUser))
        .and(assignmentStateNotHasName(EAssignmentState.DECLINED)), Sort.by(orders));
  }

  @Override
  public boolean acceptAssignment(Long assignmentId) throws DataNotFoundException {
    Optional<Assignment> optionalAssignment = assignmentRepository.findById(assignmentId);
    if (!optionalAssignment.isPresent()) {
      throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_NOT_FOUND);
    }

    Assignment assignment = optionalAssignment.get();

    AssignmentState state = assignmentStateRepository.findByName(EAssignmentState.ACCEPT)
        .orElseThrow(() -> new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_STATE_NOT_FOUND));

    Asset asset = assignment.getAsset();
    AssetState assetState = assetStateRepository.findByName(EAssetState.ASSIGNED)
        .orElseThrow(() -> new DataNotFoundException(ErrorCode.ERR_ASSET_STATE_NOT_FOUND));

    asset.setState(assetState);

    assignment.setState(state);
    assignment.setAsset(asset);

    Assignment result = assignmentRepository.save(assignment);

    if (Objects.nonNull(result)) {
      assetRepository.save(asset);

      return true;
    }

    return false;
  }

  @Override
  public boolean declinedAssignment(Long assignmentId) throws DataNotFoundException {
    Optional<Assignment> optionalAssignment = assignmentRepository.findById(assignmentId);
    if (!optionalAssignment.isPresent()) {
      throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_NOT_FOUND);
    }

    Assignment assignment = optionalAssignment.get();

    AssignmentState state = assignmentStateRepository.findByName(EAssignmentState.DECLINED)
        .orElseThrow(() -> new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_STATE_NOT_FOUND));

    Asset asset = assignment.getAsset();
    AssetState assetState = assetStateRepository.findByName(EAssetState.AVAILABLE)
        .orElseThrow(() -> new DataNotFoundException(ErrorCode.ERR_ASSET_STATE_NOT_FOUND));

    asset.setState(assetState);

    assignment.setState(state);
    assignment.setAsset(asset);

    Assignment result = assignmentRepository.save(assignment);

    if (Objects.nonNull(result)) {
      assetRepository.save(asset);

      return true;
    }

    return false;
  }

}
