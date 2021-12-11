package com.nashtech.rootkies.service;

import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.model.Assignment;
import com.nashtech.rootkies.model.HistoricalAssignment;
import com.nashtech.rootkies.model.ReturnRequest;

public interface HistoricalAssignmentService {

    boolean checkAssetInHistory(Long asssetID) throws DataNotFoundException;

    HistoricalAssignment createWhenReqAccept(ReturnRequest returnRequest) throws DataNotFoundException;

}
