package com.nashtech.rootkies.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserDTO {

    @NotBlank
    @Size(max = 20, message = "First name cannot exceed 20 characters")
    private String firstName;

    @NotBlank
    @Size(max = 20, message = "Last name cannot exceed 20 characters")
    private String lastName;

    @NotNull
    private LocalDateTime dob;

    @NotNull
    private Boolean gender;

    @NotNull
    private LocalDateTime joinedDate;

    @NotNull
    private RoleDTO role;

    @NotNull
    private Long locationId;

}
