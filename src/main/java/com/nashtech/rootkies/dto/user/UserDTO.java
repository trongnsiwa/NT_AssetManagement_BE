package com.nashtech.rootkies.dto.user;

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
public class UserDTO {

    private String code;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String gender;

    private LocalDateTime dob;

    private LocalDateTime joinedDate;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private String role;

    private String location;

    private boolean isDeleted;

}
