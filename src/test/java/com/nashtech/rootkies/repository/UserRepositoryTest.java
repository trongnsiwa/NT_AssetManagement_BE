package com.nashtech.rootkies.repository;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.enums.ERole;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.model.Location;
import com.nashtech.rootkies.model.Role;
import com.nashtech.rootkies.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
public class UserRepositoryTest {

    /*@MockBean
    User user;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    LocationRepository locationRepository;*/

    /*@Test
    public void testUpdateProductSuccess() throws Exception {
        User user = userRepository.getById("SD1111");
        Role role= roleRepository.getById(1L);

        user.setGender(true);
        user.setDob(LocalDateTime.of(1996, Month.MARCH, 28, 14, 33));
        user.setJoinedDate(LocalDateTime.of(2019, Month.MARCH, 28, 14, 33));
        user.setRole(role);

        User saveUser = userRepository.save(user);

        assertNotNull(saveUser);
        assertEquals(1L,saveUser.getRole().getId());
    }

    @Test
    public void testCreateNewUser() throws DataNotFoundException {
        User user = new User();
        user.setUsername("abc_test1");
        user.setFirstName("abc");
        user.setLastName("test");
        user.setDeleted(false);
        Optional<Location> optLoc = locationRepository.findById(1L);
        Location location;
        if (optLoc.isPresent()){
            location = optLoc.get();
        } else throw new DataNotFoundException(ErrorCode.ERR_LOCATION_NOT_FOUND);
        user.setLocation(location);
        Optional<Role> optRole = roleRepository.findByName(ERole.ROLE_ADMIN);
        Role role;
        if (optRole.isPresent()) {
            role = optRole.get();
        } else throw new DataNotFoundException(ErrorCode.ERR_ROLE_NOT_FOUND);
        user.setRole(role);
        assertNotNull(userRepository.save(user));
        assertTrue(userRepository.existsByUsername("abc_test1"));
    }*/

}
