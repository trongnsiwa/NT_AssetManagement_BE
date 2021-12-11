package com.nashtech.rootkies.service.impl;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.converter.ReturnRequestConverter;
import com.nashtech.rootkies.dto.returnrequest.CreateRequestDTO;
import com.nashtech.rootkies.dto.returnrequest.CreateReturnRequestForUserDTO;
import com.nashtech.rootkies.dto.returnrequest.ViewReturnRequestDTO;
import com.nashtech.rootkies.enums.EAssetState;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.exception.UpdateDataFailException;
import com.nashtech.rootkies.exception.UserNotFoundException;
import com.nashtech.rootkies.model.*;
import com.nashtech.rootkies.repository.*;
import com.nashtech.rootkies.service.ReturnRequestService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.nashtech.rootkies.repository.specs.ReturnRequestSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class ReturnRequestServiceImpl implements ReturnRequestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnRequestServiceImpl.class);

    @Autowired
    ReturnRequestRepository returnRequestRepository;

    @Autowired
    ReturnRequestConverter returnRequestConverter;

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AssetStateRepository assetStateRepository;

    @Override
    public ReturnRequest createReturnRequestForUser(CreateReturnRequestForUserDTO returnRequestDTO) throws CreateDataFailException, DataNotFoundException {
        try {
            //find user by username
            Optional<User> optionalUser = userRepository.findByUsername(returnRequestDTO.getRequestBy());
            if(!optionalUser.isPresent()){
                throw new UserNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
            }

            Optional<Assignment> optionalAssignment = assignmentRepository.findById(returnRequestDTO.getAssignmentId());
            if(!optionalAssignment.isPresent()){
                throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_NOT_FOUND);
            }

            Assignment assignment = optionalAssignment.get();
            if(assignment.isReturning() == true){
                throw new CreateDataFailException(ErrorCode.ERR_RETURN_REQUEST_ALREADY_EXISTED);
            }
            assignment.setReturning(true);

            User user = optionalUser.get();

            ReturnRequest entity = new ReturnRequest();
            entity.setAsset(assignment.getAsset());
            entity.setRequestBy(user);
            entity.setState(false);
            entity.setAssignedDate(assignment.getAssignedDate());
            entity.setAssignment(assignment);

            ReturnRequest result = returnRequestRepository.save(entity);

            if (Objects.nonNull(result)) {
                assignmentRepository.save(assignment);
            }

            return result;
        }catch(DataNotFoundException ex){
            throw new DataNotFoundException(ex.getMessage());
        }catch(Exception e){
            throw new CreateDataFailException(e.getMessage());
        }
    }

    @Override
    public List<ViewReturnRequestDTO> getAllRequestForAdmin(int pageNo, int pageSize, String valueSort, String direction) throws DataNotFoundException {
        List<ViewReturnRequestDTO> listDTO;
        try {
            List<Sort.Order> orders = new ArrayList<>();

            if (StringUtils.isNotEmpty(valueSort)) {
                orders.add(new Sort.Order("ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC :
                    Sort.Direction.DESC, valueSort).nullsLast());
            } else {
                orders.add(new Sort.Order(Sort.Direction.DESC, "returnedDate").nullsLast());
            }

            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(orders));
            Page<ReturnRequest> page = returnRequestRepository.findAll(where(isNotDeleted()), pageable);
            listDTO = returnRequestConverter.convertToListDTO(page.getContent());
        }catch(Exception e){
            throw new DataNotFoundException(ErrorCode.ERR_LOAD_LIST_REQUEST_FAIL);
        }
        return listDTO;
    }

    @Override
    public int recordAllRequestForAdmin() throws DataNotFoundException {
        try {
            return (int) returnRequestRepository.count(where(isNotDeleted()));
        }catch(Exception e){
            throw new DataNotFoundException(ErrorCode.ERR_LOAD_LIST_REQUEST_FAIL);
        }
    }

    @Override
    public List<ViewReturnRequestDTO> searchRequest(int pageNo, int pageSize, String valueSort, String direction, Boolean state, LocalDateTime returnDate, String keyword) throws DataNotFoundException {
        List<ViewReturnRequestDTO> listDTO;
        try {
            List<Sort.Order> orders = new ArrayList<>();

            if (StringUtils.isNotEmpty(valueSort)) {
                orders.add(new Sort.Order("ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC :
                    Sort.Direction.DESC, valueSort).nullsLast());
            } else {
                orders.add(new Sort.Order(Sort.Direction.DESC, "returnedDate").nullsLast());
            }

            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(orders));
            Page<ReturnRequest> page = returnRequestRepository.findAll(filter(state, returnDate, keyword), pageable);
            listDTO = returnRequestConverter.convertToListDTO(page.getContent());

        }catch(Exception e){
            throw new DataNotFoundException(ErrorCode.ERR_REQUEST_LIST_NOT_FOUND);
        }
        return listDTO;
    }

    @Override
    public int recordSearchRequest(Boolean state, LocalDateTime returnDate, String keyword) throws DataNotFoundException {
        try {
            return (int) returnRequestRepository.count(filter(state, returnDate, keyword));
        }catch(Exception e){
            throw new DataNotFoundException(ErrorCode.ERR_REQUEST_LIST_NOT_FOUND);
        }
    }

    @Override
    public LocalDateTime getReturnDateLastest(){
        List<ReturnRequest> listAll = returnRequestRepository.findAll(where(isNotDeleted()));
        List<ReturnRequest> listNonNull = listAll.stream()
                .filter(returnRequest -> Objects.nonNull(returnRequest.getReturnedDate()))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(listNonNull)) {
            return listNonNull.stream().map(ReturnRequest::getReturnedDate)
                .max(LocalDateTime::compareTo)
                .get();
        }

        return null;
    }

    public Specification<ReturnRequest> filter(Boolean state, LocalDateTime returnedDate, String keyword) {
        Specification<ReturnRequest> conditions = where(isNotDeleted());

        if (Objects.nonNull(returnedDate)) {
            Specification<ReturnRequest> returnedDateSpec = hasReturnedDate(returnedDate);
            conditions = conditions.and(returnedDateSpec);
        }

        if(Objects.nonNull(state)){
            Specification<ReturnRequest> stateSpec = hasState(state);
            conditions = conditions.and(stateSpec);
        }

        if (StringUtils.isNotEmpty(keyword)) {
            conditions = conditions.and(nameContainsIgnoreCase(keyword));
        }

        return conditions;
    }

    @Override
    public ReturnRequest createReturnRequest(CreateRequestDTO dto) throws CreateDataFailException {
        try {
            Optional<Assignment> optAssign = assignmentRepository.findById(dto.getAssignmentId());
            if (!optAssign.isPresent()) {
                throw new DataNotFoundException(ErrorCode.ERR_ASSIGNMENT_NOT_FOUND);
            }

            User requestUser = userRepository.findByCode(dto.getRequestBy());
            if (Objects.isNull(requestUser)) {
                throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
            }

            Assignment assignment = optAssign.get();
            if(assignment.isReturning() == true){
                throw new CreateDataFailException(ErrorCode.ERR_RETURN_REQUEST_ALREADY_EXISTED);
            }
            assignment.setReturning(true);

            ReturnRequest request = new ReturnRequest();
            request.setRequestBy(requestUser);
            request.setAsset(assignment.getAsset());
            request.setAssignedDate(assignment.getAssignedDate());
            request.setState(false);
            request.setDeleted(false);
            request.setAssignment(assignment);

            ReturnRequest result = returnRequestRepository.save(request);

            if (Objects.nonNull(result)) {
                assignmentRepository.save(assignment);
            }

            return result;
        }catch (Exception ex) {
            throw new CreateDataFailException(ex.getMessage());
        }
    }


    @Override
    public ReturnRequest acceptRequest(Long requestId, String userCode) throws UpdateDataFailException,
        DataNotFoundException {
        try{
            Optional<ReturnRequest> request= returnRequestRepository.findById(requestId);
            if(!request.isPresent()){
                LOGGER.info("ReturnRequest {} not found", requestId);
                throw new DataNotFoundException(ErrorCode.ERR_RETURN_REQUEST_NOT_FOUND);
            }
            //true Accepted false waiting for accepted
            ReturnRequest returnRequest= request.get();
            if(returnRequest.getState()==true){
                LOGGER.info("ReturnRequest {} has wrong state", requestId);
                throw new DataNotFoundException(ErrorCode.ERR_RETURN_REQUEST_STATE);
            }
            //accept by
            User user= userRepository.findByCode(userCode);
            if(Objects.isNull(user)){
                LOGGER.info("User {} not found", userCode);
                throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
            }

            //update asset state
            Asset asset = returnRequest.getAsset();
            AssetState assetState = assetStateRepository.findByName(EAssetState.AVAILABLE)
                    .orElseThrow(() -> new DataNotFoundException(ErrorCode.ERR_ASSET_STATE_NOT_FOUND));
            asset.setState(assetState);
            assetRepository.save(asset);

            //delete assignment
            Assignment assignment = returnRequest.getAssignment();
            assignment.setDeleted(true);
            assignment.setReturning(false);
//            assignmentRepository.disableAssignment(assignment.getId());

            //localdate to localdatetime
            LocalDate date= LocalDate.now();
            LocalDateTime localDateTime = date.atStartOfDay();

            returnRequest.setState(true);
            returnRequest.setAcceptedBy(user);
            returnRequest.setReturnedDate(localDateTime);
            returnRequest.setAsset(asset);

            ReturnRequest result = returnRequestRepository.save(returnRequest);

            if (Objects.nonNull(result)) {
                returnRequestRepository.save(returnRequest);
            }

            return result;
        }catch (DataNotFoundException e) {
            String message = e.getMessage();
            if (message.equals(ErrorCode.ERR_RETURN_REQUEST_STATE)) {
                throw new DataNotFoundException(ErrorCode.ERR_RETURN_REQUEST_STATE);
            }else if(message.equals(ErrorCode.ERR_USER_NOT_FOUND)){
                throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
            }else {
                throw new DataNotFoundException(ErrorCode.ERR_RETURN_REQUEST_NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.info("Fail to accept Request {}", requestId);
            throw new UpdateDataFailException(ErrorCode.ERR_RETURN_REQUEST_ACCEPT_FAIL);
        }

    }

    @Override
    public boolean cancelRequest(Long requestId) throws UpdateDataFailException, DataNotFoundException {
        try{
            Optional<ReturnRequest> request = returnRequestRepository.findById(requestId);
            if (!request.isPresent()) {
                LOGGER.info("ReturnRequest {} not found", requestId);
                throw new DataNotFoundException(ErrorCode.ERR_RETURN_REQUEST_NOT_FOUND);
            }

            ReturnRequest returnRequest = request.get();
            Assignment assignment = returnRequest.getAssignment();
            assignment.setReturning(false);

            assignmentRepository.save(assignment);

            returnRequestRepository.disableReturnRequest(requestId);
            return true;
        }
        catch (DataNotFoundException e) {
            String message = e.getMessage();
            if (message.equals(ErrorCode.ERR_RETURN_REQUEST_NOT_FOUND)) {
                throw new DataNotFoundException(ErrorCode.ERR_RETURN_REQUEST_NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.info("Fail to cancel Request {}", requestId);
            throw new UpdateDataFailException(ErrorCode.ERR_RETURN_REQUEST_CANCEL_FAIL);
        }
        return false;
    }
}
