package com.nashtech.rootkies.service.impl;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.converter.AssignmentConverter;
import com.nashtech.rootkies.dto.assigment.CreateAssignmentDTO;
import com.nashtech.rootkies.enums.EAssetState;
import com.nashtech.rootkies.enums.EAssignmentState;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.dto.assigment.UpdateAssigmentDTO;
import com.nashtech.rootkies.dto.assigment.ViewAssignmentDTO;
import com.nashtech.rootkies.dto.assigment.ViewDetailAssignmentDTO;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.DeleteDataFailException;
import com.nashtech.rootkies.exception.UpdateDataFailException;
import com.nashtech.rootkies.exception.UserNotFoundException;
import com.nashtech.rootkies.model.*;
import com.nashtech.rootkies.model.Asset;
import com.nashtech.rootkies.model.Assignment;
import com.nashtech.rootkies.repository.*;
import com.nashtech.rootkies.repository.specs.AssignmentSpecification;
import com.nashtech.rootkies.service.AssignmentService;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.nashtech.rootkies.repository.specs.AssignmentSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class AssignmentServiceImpl implements AssignmentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  AssignmentRepository assignmentRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  AssetRepository assetRepository;

  @Autowired
  AssignmentConverter assignmentConverter;

  @Autowired
  AssignmentStateRepository assignmentStateRepository;

  @Autowired
  AssetStateRepository assetStateRepository;

  @Override
  public List<ViewAssignmentDTO> getListAllAssignment(int pageNo, int pageSize, String direction, String valueSort) throws DataNotFoundException {
    List<ViewAssignmentDTO> listDTO;

    try {
      List<Sort.Order> orders = new ArrayList<>();

      if (StringUtils.isNotEmpty(valueSort)) {
        orders.add(new Sort.Order("ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC, valueSort));
      } else {
        orders.add(new Sort.Order(Sort.Direction.DESC, "assignedDate"));
      }

      Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(orders));

      Page<Assignment> page = assignmentRepository.findAll(where(isNotDeleted()), pageable);

      listDTO = assignmentConverter.toDTOList(page.getContent());
    } catch (Exception ex) {
      throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
    }
    return listDTO;
  }

  @Override
  public List<ViewAssignmentDTO> getListSearchAndFilterAssignment(int pageNo, int pageSize, String direction, String valueSort, String searchBy, List<EAssignmentState> assignmentState, LocalDateTime assignedDate) throws DataNotFoundException {
    List<ViewAssignmentDTO> listAssignmentDTOS;
    try {
      List<Sort.Order> orders = new ArrayList<>();

      if (StringUtils.isNotEmpty(valueSort)) {
        orders.add(new Sort.Order("ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC, valueSort));
      } else {
        orders.add(new Sort.Order(Sort.Direction.DESC, "assignedDate"));
      }

      Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(orders));

      Page<Assignment> page = assignmentRepository.findAll(filter(assignmentState, assignedDate, searchBy), pageable);

      listAssignmentDTOS = assignmentConverter.toDTOList(page.getContent());
    } catch (Exception e) {
      throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
    }

    return listAssignmentDTOS;
  }

  @Override
  public int getRecordListAllAssignment() throws DataNotFoundException {
    try {

      return (int) assignmentRepository.count(where(isNotDeleted()));

    } catch (Exception e) {
      throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
    }
  }

  @Override
  public int getRecordListSearchAndFilterAssignment(String searchBy, List<EAssignmentState> assignmentState, LocalDateTime assignedDate) throws DataNotFoundException {
    try {
      return (int) assignmentRepository.count(filter(assignmentState, assignedDate, searchBy));
    } catch (Exception e) {
      throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
    }
  }

  @Override
  public ViewDetailAssignmentDTO getAssignmentDetailToShow(Long id) throws DataNotFoundException {
    Optional<Assignment> optionalAssignment = assignmentRepository.findById(id);
    if (!optionalAssignment.isPresent()) {
      throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_NOT_FOUND);
    }

    Assignment assignmentEntity = optionalAssignment.get();
    return assignmentConverter.convertToViewDetailsDTO(assignmentEntity);
  }

  @Override
  public LocalDateTime getAssignDateLastest() {
    List<Assignment> listAll = assignmentRepository.findAll(where(isNotDeleted()));
    LocalDateTime maxDate = listAll.stream().map(Assignment::getAssignedDate).max(LocalDateTime::compareTo).get();
    return maxDate;
  }

  public Specification<Assignment> filter(List<EAssignmentState> assignmentState, LocalDateTime assignedDate, String keyword) {
    Specification<Assignment> conditions = where(isNotDeleted());

    if (Objects.nonNull(assignedDate)) {
      Specification<Assignment> assignmentSpec = assignmentHasAssignedDate(assignedDate);
      conditions = conditions.and(assignmentSpec);
    }

    if (!CollectionUtils.isEmpty(assignmentState)) {
      boolean first = true;

      Specification<Assignment> stateSpec = null;
      for (EAssignmentState state : assignmentState) {
        if (first) {
          stateSpec = assignmentStateHasName(state);
          first = false;
        } else {
          stateSpec = stateSpec.or(assignmentStateHasName(state));
        }
        stateSpec = stateSpec.or(assignmentStateHasName(state));
      }
      conditions = conditions.and(stateSpec);
    }

    if (StringUtils.isNotEmpty(keyword)) {
      conditions = conditions.and(nameContainsIgnoreCaseAssignment(keyword));
    }

    return conditions;
  }


  @Override
  public boolean checkIfValidAssignment(String staffCode) throws UserNotFoundException {
    try {
      Optional<User> optionalUser = userRepository.getUserByCode(staffCode);
      if (!optionalUser.isPresent()) {
        LOGGER.info("User {} is not found", staffCode);
        throw new UserNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
      }

      return assignmentRepository.count(where(isNotDeleted()).and(hasUser(optionalUser.get()))) > 0;
    } catch (Exception ex) {
      throw new UserNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
    }
  }

  @Override
  public Assignment updateAssigment(UpdateAssigmentDTO assignmentDTO, Long id) throws UpdateDataFailException,
      DataNotFoundException, UserNotFoundException {
    try {
      Optional<Assignment> optionalAssignment = assignmentRepository.findById(id);
      if (!optionalAssignment.isPresent()) {
        LOGGER.info("Cannot find assigment with id", id);
        throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_NOT_FOUND);
      }

      Optional<User> user = userRepository.findByUsername(assignmentDTO.getUsername());
      if (!user.isPresent()) {
        LOGGER.info("User {} is not found", assignmentDTO.getUsername());
        throw new UserNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
      }

      Optional<Asset> asset = assetRepository.findById(assignmentDTO.getAssetId());
      if (!asset.isPresent()) {
        LOGGER.info("Asset {} is not found", assignmentDTO.getAssetId());
        throw new DataNotFoundException(ErrorCode.ERR_ASSET_NOT_FOUND);
      }

      Optional<Asset> previousAsset = assetRepository.findById(assignmentDTO.getPrevAssetId());
      if (!previousAsset.isPresent()) {
        LOGGER.info("Old Asset {} is not found", assignmentDTO.getPrevAssetId());
        throw new DataNotFoundException(ErrorCode.ERR_ASSET_NOT_FOUND);
      }

      Asset changeStateAsset = asset.get();
      Asset changeStatePreviousAsset = previousAsset.get();

      if (!changeStateAsset.equals(changeStatePreviousAsset)) {
        AssetState assetState = assetStateRepository.findByName(EAssetState.WAITING_FOR_ASSIGNED)
            .orElseThrow(() -> new DataNotFoundException(ErrorCode.ERR_ASSET_STATE_NOT_FOUND));
        changeStateAsset.setState(assetState);

        AssetState oldAssetState = assetStateRepository.findByName(EAssetState.AVAILABLE)
            .orElseThrow(() -> new DataNotFoundException(ErrorCode.ERR_ASSET_STATE_NOT_FOUND));
        changeStatePreviousAsset.setState(oldAssetState);
      }

      Assignment assignmentEntity = optionalAssignment.get();
      assignmentEntity.setId(assignmentDTO.getId());
      assignmentEntity.setAssignedTo(user.get());
      assignmentEntity.setAsset(changeStateAsset);
      assignmentEntity.setAssignedDate(assignmentDTO.getAssignDate());
      assignmentEntity.setNote(assignmentDTO.getNote());

      Assignment result = assignmentRepository.save(assignmentEntity);

      if (Objects.nonNull(result) && !changeStateAsset.equals(changeStatePreviousAsset)) {
        assetRepository.save(changeStateAsset);
        assetRepository.save(changeStatePreviousAsset);
      }

      return result;
    } catch (DataNotFoundException e) {
      throw new DataNotFoundException(e.getMessage());
    } catch (UserNotFoundException e) {
      throw new UserNotFoundException(e.getMessage());
    } catch (Exception e) {
      throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_ASSIGNMENT_FAIL);
    }
  }


  @Override
  public Assignment createAssignment(CreateAssignmentDTO createAssignmentDTO)
      throws CreateDataFailException, UserNotFoundException, DataNotFoundException {
    try {
      User assignTo = userRepository.findByCode(createAssignmentDTO.getStaffCode());
      if (Objects.isNull(assignTo)) {
        throw new UserNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
      }

      Asset asset = assetRepository.findById(createAssignmentDTO.getAssetId())
          .orElseThrow(() -> new DataNotFoundException(ErrorCode.ERR_ASSET_NOT_FOUND));

      User assignBy = userRepository.findByCode(createAssignmentDTO.getAssignBy());
      if (Objects.isNull(assignTo)) {
        throw new UserNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
      }

      AssignmentState state = assignmentStateRepository.findByName(EAssignmentState.WAITING_FOR_ACCEPTANCE)
          .orElseThrow(() -> new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_STATE_NOT_FOUND));

      AssetState assetState = assetStateRepository.findByName(EAssetState.WAITING_FOR_ASSIGNED)
          .orElseThrow(() -> new DataNotFoundException(ErrorCode.ERR_ASSET_STATE_NOT_FOUND));
      asset.setState(assetState);

      Assignment assignment = new Assignment();
      assignment.setState(state);
      assignment.setAssignedTo(assignTo);
      assignment.setAsset(asset);
      assignment.setAssignedBy(assignBy);
      assignment.setAssignedDate(createAssignmentDTO.getAssignedDate());
      assignment.setNote(createAssignmentDTO.getNote());
      assignment.setDeleted(false);

      Assignment result = assignmentRepository.save(assignment);

      if (Objects.nonNull(result)) {
        assetRepository.save(asset);
      }

      return result;
    } catch (DataNotFoundException e) {
      throw new DataNotFoundException(ErrorCode.ERR_ASSET_NOT_FOUND);
    } catch (UserNotFoundException e) {
      throw new UserNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
    } catch (Exception e) {
      throw new CreateDataFailException(ErrorCode.ERR_CREATE_ASSIGNMENT_FAIL);
    }
  }

  @Override
  public int deleteAssignment(Long id) throws DeleteDataFailException, DataNotFoundException {
    Optional<Assignment> optAssign = assignmentRepository.findById(id);
    Assignment assignment;
    if (optAssign.isPresent()) {
      assignment = optAssign.get();
    } else {
      throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_NOT_FOUND);
    }

    int result = 0;

    if (assignment.getState().getName().toString().equals("ACCEPT")) {
      throw new DeleteDataFailException(ErrorCode.ERR_ASSIGNMENT_STATE_ACCEPT);
    } else {
      result = assignmentRepository.deleteAssignment(id);
    }

    if (result == 0) {
      throw new DeleteDataFailException(ErrorCode.ERR_DELETE_ASSIGNMENT_FAIL);
    }

    Asset asset = optAssign.get().getAsset();
    AssetState assetState = assetStateRepository.findByName(EAssetState.AVAILABLE)
        .orElseThrow(() -> new DataNotFoundException(ErrorCode.ERR_ASSET_STATE_NOT_FOUND));
    asset.setState(assetState);
    assetRepository.save(asset);

    return result;
  }

}
