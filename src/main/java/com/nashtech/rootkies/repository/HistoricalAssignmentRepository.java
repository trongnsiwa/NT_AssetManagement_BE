package com.nashtech.rootkies.repository;

import com.nashtech.rootkies.model.Asset;
import com.nashtech.rootkies.model.HistoricalAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoricalAssignmentRepository extends JpaRepository<HistoricalAssignment, Long> {

    List<HistoricalAssignment> findByAsset(Asset asset);

}
