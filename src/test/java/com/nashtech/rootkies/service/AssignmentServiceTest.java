//package com.nashtech.rootkies.service;
//
//import com.nashtech.rootkies.converter.AssignmentConverter;
//import com.nashtech.rootkies.dto.assigment.CreateAssignmentDTO;
//import com.nashtech.rootkies.dto.assigment.UpdateAssigmentDTO;
//import com.nashtech.rootkies.enums.EAssetState;
//import com.nashtech.rootkies.enums.EAssignmentState;
//import com.nashtech.rootkies.exception.*;
//import com.nashtech.rootkies.model.*;
//import com.nashtech.rootkies.repository.*;
//import com.nashtech.rootkies.repository.specs.AssignmentSpecification;
//import com.nashtech.rootkies.service.impl.AssignmentServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.Month;
//import java.util.List;
//import java.util.Optional;
//
//import static com.nashtech.rootkies.repository.specs.AssignmentSpecification.assignedThanCurrentDate;
//import static com.nashtech.rootkies.repository.specs.AssignmentSpecification.isNotDeleted;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.springframework.data.jpa.domain.Specification.where;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AssignmentServiceTest {
//    @Autowired
//    AssignmentServiceImpl assignmentService;
//
//    @MockBean
//    AssignmentRepository assignmentRepository;
//
//    @MockBean
//    AssignmentStateRepository stateRepository;
//
//    @MockBean
//    AssetRepository assetRepository;
//
//    @MockBean
//    AssetStateRepository assetStateRepository;
//
//    @MockBean
//    UserRepository userRepository;
//
//    @MockBean
//    AssignmentConverter converter;
//
////    @Mock
////    Specification<Assignment> mockSpec;
//
////    @Mock
////    Root<Assignment> root;
////
////    @Mock
////    CriteriaQuery<?> query;
////
////    @Mock
////    CriteriaBuilder builder;
//
//    @Test
//    public void testEditAssignment_thenReturnAssigment()
//        throws DataNotFoundException, UpdateDataFailException, UserNotFoundException {
//
//        //asset state to update
//        AssetState assetState = new AssetState();
//        assetState.setName(EAssetState.AVAILABLE);
//
//        AssetState assetState2 = new AssetState();
//        assetState2.setName(EAssetState.WAITING_FOR_ASSIGNED);
//
//        //user of assigment fake
//        User assignTo = new User();
//        assignTo.setCode("SD0002");
//        assignTo.setUsername("robin");
//
//        //asset of assignment fake
//        Asset asset = new Asset();
//        asset.setId(1L);
//        asset.setCode("IP003");
//        asset.setName("Iphone 7");
//        asset.setState(assetState2);
//
//        //user of assignment fake
//        User assignBy = new User();
//        assignBy.setCode("SD0001");
//
//        //assignmentState of assignment fake
//        AssignmentState assignmentState = new AssignmentState();
//        assignmentState.setName(EAssignmentState.WAITING_FOR_ACCEPTANCE);
//
//        //assignment fake
//        Assignment assignment = new Assignment();
//        assignment.setAssignedDate(LocalDateTime.of(2021, Month.AUGUST, 15 , 0, 0, 0));
//        assignment.setState(assignmentState);
//        assignment.setAssignedBy(assignBy);
//        assignment.setAssignedTo(assignTo);
//        assignment.setAsset(asset);
//        assignment.setId(1L);
//
//        //user to update
//        User user2 = new User();
//        user2.setUsername("tinnt");
//        user2.setCode("SD0003");
//
//        //asset to update
//        Asset asset2 = new Asset();
//        asset2.setId(2L);
//        asset2.setCode("IP004");
//        asset2.setName("Iphone 10");
//        asset2.setState(assetState);
//
//        //update assigment dto
//        UpdateAssigmentDTO update = new UpdateAssigmentDTO();
//        update.setId(1L);
//        update.setNote("abc");
//        update.setAssignDate(LocalDateTime.of(2021, Month.AUGUST, 20 , 0, 0, 0));
//        update.setUsername("tinnt");
//        update.setAssetId(2L);
//        update.setPrevAssetId(1L);
//
//        when(assignmentRepository.findById(update.getId())).thenReturn(Optional.of(assignment));
//
//        when(userRepository.findByUsername(update.getUsername())).thenReturn(Optional.of(user2));
//
//        when(assetRepository.findById(update.getAssetId())).thenReturn(Optional.of(asset2));
//
//        when(assetRepository.findById(update.getPrevAssetId())).thenReturn(Optional.of((asset)));
//
//        when(assetStateRepository.findByName(EAssetState.WAITING_FOR_ASSIGNED)).thenReturn(Optional.of(assetState2));
//
//        when(assetStateRepository.findByName(EAssetState.AVAILABLE)).thenReturn(Optional.of(assetState));
//
//        when(assignmentRepository.save(assignment)).thenReturn(assignment);
//
//        assignment = assignmentService.updateAssigment(update, update.getId());
//
//        assertEquals(update.getAssetId(), assignment.getAsset().getId());
//        assertEquals(update.getNote(), assignment.getNote());
//        assertEquals(update.getUsername(), assignment.getAssignedTo().getUsername());
//        assertEquals(update.getAssignDate(), assignment.getAssignedDate());
//
//    }
//
//    @Test
//    public void testCreateAssignment()
//            throws ConvertEntityDTOException, UserNotFoundException, DataNotFoundException, CreateDataFailException {
//        CreateAssignmentDTO createAssignmentDTO = new CreateAssignmentDTO();
//        createAssignmentDTO.setStaffCode("SD0001");
//        createAssignmentDTO.setAssignBy("SD0002");
//        createAssignmentDTO.setAssetId(1L);
//        createAssignmentDTO.setAssignedDate(LocalDateTime.of(2021, Month.AUGUST, 12 , 0, 0, 0));
//
//        Assignment assignment = new Assignment();
//        assignment.setId(1L);
//        User assignTo = new User();
//        assignTo.setCode("SD0001");
//
//        Asset asset = new Asset();
//        asset.setCode("LA0001");
//
//        User assignBy = new User();
//        assignBy.setCode("SD0002");
//
//        AssignmentState assignmentState = new AssignmentState();
//        assignmentState.setName(EAssignmentState.WAITING_FOR_ACCEPTANCE);
//
//        AssetState assetState = new AssetState();
//        assetState.setName(EAssetState.WAITING_FOR_ASSIGNED);
//
//        assignment.setAssignedDate(LocalDateTime.of(2021, Month.AUGUST, 11 , 0, 0, 0));
//
//        assignment.setState(assignmentState);
//        assignment.setAssignedBy(assignBy);
//        assignment.setAssignedTo(assignTo);
//        assignment.setAsset(asset);
//
//        assertThrows(UserNotFoundException.class,() -> assignmentService.createAssignment(createAssignmentDTO));
//
//        when(userRepository.findByCode(anyString())).thenReturn(assignTo);
//
//        assertThrows(DataNotFoundException.class,() -> assignmentService.createAssignment(createAssignmentDTO));
////
//        when(assetRepository.findById(anyLong())).thenReturn(java.util.Optional.of(asset));
////
//        when(userRepository.findByCode(anyString())).thenReturn(assignBy);
////
//        when(stateRepository.findByName(EAssignmentState.WAITING_FOR_ACCEPTANCE))
//                .thenReturn(java.util.Optional.of(assignmentState));
////
//        when(assetStateRepository.findByName(EAssetState.WAITING_FOR_ASSIGNED)).thenReturn(Optional.of(assetState));
//
//        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);
//
////
//        Assignment newAssign = assignmentService.createAssignment(createAssignmentDTO);
////
//        assertEquals(LocalDateTime.of(2021, Month.AUGUST, 11 , 0, 0, 0),newAssign.getAssignedDate());
//        assertEquals("SD0001", newAssign.getAssignedTo().getCode());
//        assertEquals(EAssignmentState.WAITING_FOR_ACCEPTANCE, newAssign.getState().getName());
//
//    }
//}
