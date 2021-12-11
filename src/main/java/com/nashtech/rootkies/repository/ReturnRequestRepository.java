package com.nashtech.rootkies.repository;

import com.nashtech.rootkies.model.Asset;
import com.nashtech.rootkies.model.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long>,JpaSpecificationExecutor<ReturnRequest> {

    @Transactional
    @Modifying
    @Query(value = "update return_request set is_deleted = true where id = ?1", nativeQuery = true)
    int disableReturnRequest(Long id);
}