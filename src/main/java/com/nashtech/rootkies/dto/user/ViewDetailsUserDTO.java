package com.nashtech.rootkies.dto.user;

import com.nashtech.rootkies.dto.user.request.RoleDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewDetailsUserDTO {

    private String code;

    private String username;

    private String firstName;

    private String lastName;

    private LocalDateTime dob;

    private Boolean gender;

    private LocalDateTime joinedDate;

    private RoleDTO role;

    private String locationName;

}
