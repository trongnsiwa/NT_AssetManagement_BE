package com.nashtech.rootkies.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nashtech.rootkies.model.User;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @Query(value = "select * from users where code = ?1", nativeQuery = true)
    Optional<User> getUserByCode(String code);

    @Transactional
    @Modifying
    @Query(value = "update users set is_deleted = true where code = ?1", nativeQuery = true)
    int disableUser (String code);

    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    User findByCode(String code);

}
