package com.nashtech.rootkies.converter;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.dto.assigment.*;
import com.nashtech.rootkies.exception.ConvertEntityDTOException;
import com.nashtech.rootkies.model.Assignment;
import com.nashtech.rootkies.repository.AssetRepository;
import com.nashtech.rootkies.repository.AssetStateRepository;
import com.nashtech.rootkies.repository.AssignmentRepository;
import com.nashtech.rootkies.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssignmentConverter {

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  AssignmentRepository assignmentRepository;

  @Autowired
  AssetRepository assetRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  AssetStateRepository assetStateRepository;


  public ViewAssignmentDTO convertToAssignmentDTO(Assignment assignment) {

    ViewAssignmentDTO assignmentDTO = modelMapper.map(assignment, ViewAssignmentDTO.class);
    assignmentDTO.setId(assignment.getId());
    assignmentDTO.setAssetCode(String.valueOf(assignment.getAsset().getCode()));
    assignmentDTO.setAssetName(String.valueOf(assignment.getAsset().getName()));
    assignmentDTO.setAssignedBy(assignment.getAssignedBy().getUsername());
    assignmentDTO.setAssignedTo(assignment.getAssignedTo().getUsername());
    assignmentDTO.setAssignedDate(assignment.getAssignedDate());
    assignmentDTO.setState(assignment.getState().getName().name());
    assignmentDTO.setReturning(assignment.isReturning());
    return assignmentDTO;
  }

  public List<ViewAssignmentDTO> toDTOList(List<Assignment> listEntity) throws ConvertEntityDTOException {
    List<ViewAssignmentDTO> listDTO;

    try {
      listDTO = listEntity.stream().map(this::convertToAssignmentDTO).collect(Collectors.toList());
    } catch (Exception ex) {
      throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERT_DTO_ENTITY_FAIL);
    }
    return listDTO;
  }

  public ViewDetailAssignmentDTO convertToViewDetailsDTO(Assignment assignment) {

    ViewDetailAssignmentDTO detailAssignmentDTO = modelMapper.map(assignment, ViewDetailAssignmentDTO.class);

    detailAssignmentDTO.setAssetCode(assignment.getAsset().getCode());
    detailAssignmentDTO.setAssetName(String.valueOf(assignment.getAsset().getName()));
    detailAssignmentDTO.setSpecification(assignment.getAsset().getSpecification());
    detailAssignmentDTO.setAssignedTo(assignment.getAssignedTo().getUsername());
    detailAssignmentDTO.setAssignedBy(assignment.getAssignedBy().getUsername());
    detailAssignmentDTO.setAssignedDate(assignment.getAssignedDate());
    detailAssignmentDTO.setState(assignment.getState().getName().name());
    detailAssignmentDTO.setNote(assignment.getNote());
    detailAssignmentDTO.setAssetId(assignment.getAsset().getId());
    detailAssignmentDTO.setAssignedToFullName(
        assignment.getAssignedTo().getFirstName() + " " + assignment.getAssignedTo().getLastName());

    return detailAssignmentDTO;
  }

  public AssignmentDTO convertToDTO(Assignment assignment) {
    AssignmentDTO assignmentDTO = modelMapper.map(assignment, AssignmentDTO.class);
    assignmentDTO.setAssetCode(assignment.getAsset().getCode());
    assignmentDTO.setAssetName(assignment.getAsset().getName());
    assignmentDTO.setAssignTo(assignment.getAssignedTo().getUsername());
    assignmentDTO.setAssignBy(assignment.getAssignedBy().getUsername());
    assignmentDTO.setState(assignment.getState().getName());
    assignmentDTO.setCategoryName(assignment.getAsset().getCategory().getName());
    assignmentDTO.setReturning(assignment.isReturning());

    return assignmentDTO;
  }

  public List<AssignmentDTO> convertToDTOList(List<Assignment> assignments) throws ConvertEntityDTOException {
    try {
      return assignments.stream().map(this::convertToDTO).collect(Collectors.toList());
    } catch (Exception e) {
      throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERT_DTO_ENTITY_FAIL);
    }
  }

}


