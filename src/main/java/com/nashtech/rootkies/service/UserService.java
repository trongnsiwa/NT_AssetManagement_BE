package com.nashtech.rootkies.service;

import com.nashtech.rootkies.dto.user.ViewDetailsUserDTO;
import com.nashtech.rootkies.dto.user.ViewUserDTO;
import com.nashtech.rootkies.dto.user.request.UpdatePasswordUserDTO;
import com.nashtech.rootkies.dto.user.request.UpdateUserDTO;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.dto.user.request.CreateUserDTO;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DeleteDataFailException;
import com.nashtech.rootkies.exception.UpdateDataFailException;
import com.nashtech.rootkies.exception.UserAuthenticationException;
import com.nashtech.rootkies.exception.UserNotFoundException;
import com.nashtech.rootkies.model.User;
import com.nashtech.rootkies.payload.request.LoginRequest;
import com.nashtech.rootkies.payload.response.JwtResponse;

import java.util.List;

public interface UserService {

    List<ViewUserDTO> getListAllUserForAdmin(int pageNo, int pageSize, String valueSort, String direction,
                                             Long locationID) throws DataNotFoundException;

    List<ViewUserDTO> getListSearchAndFilterUserForAdmin(int pageNo, int pageSize, String valueSort, String direction,
                                                         Long locationID, String searchBy, Long typeID) throws DataNotFoundException;

    int getRecordListAllUser(Long locationID) throws DataNotFoundException;

    int getRecordListSearchAndFilterUser(Long locationID, String searchBy, Long typeID) throws DataNotFoundException;

    int disableUser(String code) throws UpdateDataFailException, UserNotFoundException, DeleteDataFailException, DataNotFoundException;

    User createNewUser(CreateUserDTO dto) throws CreateDataFailException;

    ViewDetailsUserDTO getUserDetailsToShow(String code) throws DataNotFoundException;

    User updateUser(UpdateUserDTO updateUserDTO, String id) throws UpdateDataFailException, DataNotFoundException;

    JwtResponse authenticateAccount(LoginRequest loginRequest) throws UserAuthenticationException;

    User updatePasswordUserFirstLogin(UpdatePasswordUserDTO updateUserDTO) throws UpdateDataFailException, DataNotFoundException;

    boolean updatePassword(UpdatePasswordUserDTO updateUserDTO) throws UpdateDataFailException, DataNotFoundException;

    boolean checkDeletedAccount(String code) throws UserNotFoundException, DataNotFoundException;

}
