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
public class ViewUserDTO {

    private String code;

    private String username;

    private String fullName;

    private LocalDateTime joinedDate;

    private String roleName;

    private Long locationId;

}
