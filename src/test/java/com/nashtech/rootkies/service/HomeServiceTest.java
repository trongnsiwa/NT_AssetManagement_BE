//package com.nashtech.rootkies.service;
//
//import com.nashtech.rootkies.enums.EAssetState;
//import com.nashtech.rootkies.enums.EAssignmentState;
//import com.nashtech.rootkies.exception.DataNotFoundException;
//import com.nashtech.rootkies.model.Asset;
//import com.nashtech.rootkies.model.AssetState;
//import com.nashtech.rootkies.model.Assignment;
//import com.nashtech.rootkies.model.AssignmentState;
//import com.nashtech.rootkies.repository.*;
//import com.nashtech.rootkies.service.impl.AssignmentServiceImpl;
//import com.nashtech.rootkies.service.impl.HomeServiceImpl;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
//import static org.junit.Assert.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class HomeServiceTest {
//
//  @Autowired
//  AssignmentServiceImpl assignmentService;
//
//  @Autowired
//  HomeServiceImpl homeService;
//
//  @MockBean
//  AssignmentRepository assignmentRepository;
//
//  @MockBean
//  AssetRepository assetRepository;
//
//  @MockBean
//  UserRepository userRepository;
//
//  @MockBean
//  AssignmentStateRepository assignmentStateRepository;
//
//  @MockBean
//  AssetStateRepository assetStateRepository;
//
//  @Before
//  public void init() {
//    AssignmentState assignmentState1 = new AssignmentState();
//    assignmentState1.setId(1L);
//    assignmentState1.setName(EAssignmentState.WAITING_FOR_ACCEPTANCE);
//
//    AssignmentState assignmentState2 = new AssignmentState();
//    assignmentState2.setId(2L);
//    assignmentState2.setName(EAssignmentState.ACCEPT);
//
//    AssignmentState assignmentState3 = new AssignmentState();
//    assignmentState3.setId(3L);
//    assignmentState3.setName(EAssignmentState.DECLINED);
//
//    when(assignmentStateRepository.findByName(EAssignmentState.WAITING_FOR_ACCEPTANCE)).thenReturn(Optional.of(assignmentState1));
//    when(assignmentStateRepository.findByName(EAssignmentState.ACCEPT)).thenReturn(Optional.of(assignmentState2));
//    when(assignmentStateRepository.findByName(EAssignmentState.DECLINED)).thenReturn(Optional.of(assignmentState3));
//
//    AssetState assetState = new AssetState();
//    assetState.setId(1L);
//    assetState.setName(EAssetState.WAITING_FOR_ASSIGNED);
//
//    AssetState assetState2 = new AssetState();
//    assetState2.setId(2L);
//    assetState2.setName(EAssetState.ASSIGNED);
//
//    AssetState assetState3 = new AssetState();
//    assetState3.setId(3L);
//    assetState3.setName(EAssetState.AVAILABLE);
//
//    when(assetStateRepository.findByName(EAssetState.WAITING_FOR_ASSIGNED)).thenReturn(Optional.of(assetState));
//    when(assetStateRepository.findByName(EAssetState.ASSIGNED)).thenReturn(Optional.of(assetState2));
//    when(assetStateRepository.findByName(EAssetState.AVAILABLE)).thenReturn(Optional.of(assetState3));
//
//    Asset asset = new Asset();
//    asset.setId(1L);
//    asset.setState(assetState);
//
//    Long assigmentId = 1L;
//    Assignment assignment = new Assignment();
//    assignment.setId(assigmentId);
//    assignment.setDeleted(false);
//    assignment.setState(assignmentState1);
//
//    when(assignmentRepository.findById(assigmentId)).thenReturn(Optional.of(assignment));
//  }
//
//  @Test
//  public void testGetListAssignmentOfUser() throws DataNotFoundException {
////        String userCode = "1";
////        User user = new User();
////        user.setCode(userCode);
////
////        when(userRepository.findByCode(userCode)).thenReturn(user);
////
////        Assignment assignment1 = new Assignment();
////        assignment1.setId(1L);
////        assignment1.setDeleted(false);
////        assignment1.setAssignedDate(LocalDateTime.of(2021, 8, 12, 0, 0));
////
////        Assignment assignment2 = new Assignment();
////        assignment2.setId(2L);
////        assignment2.setDeleted(false);
////        assignment2.setAssignedDate(LocalDateTime.of(2021, 8, 15, 0, 0));
////
////        Assignment assignment3 = new Assignment();
////        assignment3.setId(3L);
////        assignment3.setAssignedDate(LocalDateTime.of(2021, 9, 15, 0, 0));
////
////        LocalDate today = LocalDate.now();
////        LocalDateTime todayWithTime = today.atStartOfDay();
////
////        when(assignmentRepository.findAll(where(assignedThanCurrentDate(todayWithTime)).and(isNotDeleted())))
////            .thenReturn(List.of(assignment1, assignment2));
////
////        assertEquals(2, assignmentService.getListAssignmentOfUser(userCode).size());
//  }
//
//  @Test
//  public void testAcceptAssignment() throws DataNotFoundException {
////    AssignmentState assignmentState2 = new AssignmentState();
////    assignmentState2.setId(2L);
////    assignmentState2.setName(EAssignmentState.ACCEPT);
////
////    AssetState assetState2 = new AssetState();
////    assetState2.setId(2L);
////    assetState2.setName(EAssetState.ASSIGNED);
////
////    Asset asset = new Asset();
////    asset.setId(1L);
////    asset.setState(assetState2);
////
////    Long assignmentId = 1L;
////    Assignment acceptAssignment = new Assignment();
////    acceptAssignment.setId(assignmentId);
////    acceptAssignment.setDeleted(false);
////    acceptAssignment.setState(assignmentState2);
////    acceptAssignment.setAsset(asset);
////
////    when(assignmentRepository.save(acceptAssignment)).thenReturn(acceptAssignment);
////    when(assetRepository.save(asset)).thenReturn(asset);
////
////    when(homeService.acceptAssignment(1L)).thenReturn(true);
////
////    assertTrue(homeService.acceptAssignment(1L));
//  }
//
//
//}
