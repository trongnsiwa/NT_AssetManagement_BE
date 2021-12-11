//package com.nashtech.rootkies.service;
//
//import com.nashtech.rootkies.dto.user.request.UpdateUserDTO;
//import com.nashtech.rootkies.enums.ERole;
//import com.nashtech.rootkies.exception.DataNotFoundException;
//import com.nashtech.rootkies.exception.DeleteDataFailException;
//import com.nashtech.rootkies.exception.UserNotFoundException;
//import com.nashtech.rootkies.model.Location;
//import com.nashtech.rootkies.model.Role;
//import com.nashtech.rootkies.model.User;
//import com.nashtech.rootkies.repository.LocationRepository;
//import com.nashtech.rootkies.repository.RoleRepository;
//import com.nashtech.rootkies.repository.UserRepository;
//import com.nashtech.rootkies.service.impl.UserServiceImpl;
//
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.LocalDateTime;
//import java.time.Month;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UserServiceTest {
//
//    @MockBean
//    UserRepository userRepository;
//
//    @MockBean
//    RoleRepository roleRepository;
//
//    @MockBean
//    LocationRepository locationRepository;
//
//    @MockBean
//    ModelMapper modelMapper;
//
//    @Autowired
//    UserServiceImpl userService;
//
//    /*@BeforeEach
//    public void setUp() throws Exception {
//        Role role= new Role();
//        role.setId(3L);
//        role.setName(ERole.ROLE_ADMIN);
//        Mockito.when(roleRepository.findById(3L)).thenReturn(Optional.of(role));
//
//        Location location= new Location();
//        location.setId(3L);
//        location.setName("HCM");
//        Mockito.when(locationRepository.findById(3L)).thenReturn(Optional.of(location));
//
//        User user = new User();
//        user.setCode("SD1111");
//        user.setFirstName("asdf");
//        user.setLastName("qwer");
//        user.setPassword("123456");
//        user.setRole(role);
//        user.setGender(false);
//        user.setCreatedDate(LocalDateTime.of(2021, Month.MARCH, 28, 14, 33));
//        user.setDob(LocalDateTime.of(1990, Month.MARCH, 28, 14, 33));
//        user.setJoinedDate(LocalDateTime.of(2021, Month.MARCH, 28, 14, 33));
//        user.setUpdatedDate(LocalDateTime.of(2022, Month.MARCH, 28, 14, 33));
//        user.setLocation(location);
//        user.setDeleted(false);
//
//        Mockito.when(userRepository.findById("SD1113")).thenReturn(Optional.of(user));
//    }
//
//    @Test
//    public void testUpdateUserSuccess() throws Exception {
//        UpdateUserDTO userDTO = new UpdateUserDTO();
//
//        userDTO.setCode("SD1111");
//        userDTO.setDob(LocalDateTime.of(1990, Month.JUNE, 28, 14, 33));
//        userDTO.setJoinedDate(LocalDateTime.of(2020, Month.APRIL, 28, 14, 33));
//        userDTO.setGender(true);
//        userDTO.setRoleId(3L);
//
//        Role role= roleRepository.findById(3L).get();
//        Location location=locationRepository.findById(3L).get();
//
//        User user= new User();
//        user.setCode("SD1111");
//        user.setFirstName("asdf");
//        user.setLastName("qwer");
//        user.setPassword("123456");
//        user.setRole(role);
//        user.setGender(false);
//        user.setCreatedDate(LocalDateTime.of(2021, Month.MARCH, 28, 14, 33));
//        user.setDob(LocalDateTime.of(1990, Month.MARCH, 28, 14, 33));
//        user.setJoinedDate(LocalDateTime.of(2021, Month.MARCH, 28, 14, 33));
//        user.setUpdatedDate(LocalDateTime.of(2022, Month.MARCH, 28, 14, 33));
//        user.setLocation(location);
//        user.setDeleted(false);
//        Mockito.when(userRepository.save(user)).thenReturn(user);
//
////        User temp= userService.updateUser(userDTO,"SD1111");
////        assertNotNull(temp);
//        assertNotNull(user);
////      //  assertEquals(3L, user1.getRole().getId());
////        Mockito.verify(userRepository).save(any(User.class));
//    }*/
//
//    @Test
//    public void testDisableUser_ReturnShouldBe1()
//            throws UserNotFoundException, DataNotFoundException, DeleteDataFailException {
//        User user = new User();
//        user.setCode("SD001");
//        user.setUsername("robin");
//        user.setDeleted(false);
//
//        when(userRepository.getUserByCode("SD001")).thenReturn(Optional.of(user));
//        when(userRepository.disableUser("SD001")).thenReturn(1);
//        when(userService.disableUser("SD001")).thenReturn(1);
//
//        int result = userService.disableUser("SD001");
//
//        assertEquals(1, result);
//    }
//}
