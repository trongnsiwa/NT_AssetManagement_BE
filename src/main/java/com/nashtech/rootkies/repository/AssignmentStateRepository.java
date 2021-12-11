package com.nashtech.rootkies.repository;

import com.nashtech.rootkies.enums.EAssignmentState;
import com.nashtech.rootkies.model.AssignmentState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignmentStateRepository extends JpaRepository<AssignmentState, Long> {

    Optional<AssignmentState> findByName(EAssignmentState name);

}