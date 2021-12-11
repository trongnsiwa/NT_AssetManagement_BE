package com.nashtech.rootkies.service;

import com.nashtech.rootkies.dto.returnrequest.CreateRequestDTO;
import com.nashtech.rootkies.dto.returnrequest.CreateReturnRequestForUserDTO;
import com.nashtech.rootkies.dto.returnrequest.ViewReturnRequestDTO;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.UpdateDataFailException;
import com.nashtech.rootkies.model.Assignment;
import com.nashtech.rootkies.model.ReturnRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface ReturnRequestService {

    ReturnRequest createReturnRequestForUser(CreateReturnRequestForUserDTO returnRequestDTO) throws CreateDataFailException, DataNotFoundException;

    List<ViewReturnRequestDTO> getAllRequestForAdmin(int pageNo, int pageSize, String valueSort, String direction) throws DataNotFoundException;

    int recordAllRequestForAdmin() throws DataNotFoundException;

    List<ViewReturnRequestDTO> searchRequest(int pageNo, int pageSize, String valueSort, String direction, Boolean state, LocalDateTime returnDate, String keyword) throws DataNotFoundException;

    int recordSearchRequest(Boolean state, LocalDateTime returnDate, String keyword) throws DataNotFoundException;

    LocalDateTime getReturnDateLastest() throws DataNotFoundException;

    ReturnRequest createReturnRequest(CreateRequestDTO dto) throws CreateDataFailException;

    ReturnRequest acceptRequest(Long requestId, String userCode) throws UpdateDataFailException, DataNotFoundException;

    boolean cancelRequest(Long requestId) throws UpdateDataFailException, DataNotFoundException;

}
