package com.nashtech.rootkies.repository;

import com.nashtech.rootkies.model.Asset;
import com.nashtech.rootkies.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long>, JpaSpecificationExecutor<Assignment> {

    Optional<Assignment> findByAsset(Asset asset);

    @Transactional
    @Modifying
    @Query(value = "update assignments set is_deleted = true where id = ?1", nativeQuery = true)
    int deleteAssignment(Long id);

    @Transactional
    @Modifying
    @Query(value = "update assignments set is_deleted = true where id = ?1", nativeQuery = true)
    int disableAssignment(Long id);

}
