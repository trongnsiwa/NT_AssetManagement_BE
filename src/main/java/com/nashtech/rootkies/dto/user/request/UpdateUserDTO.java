package com.nashtech.rootkies.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserDTO {

    @NotBlank
    private String code;

    @Size(max = 20)
    private String firstName;

    @Size(max = 20)
    private String lastName;

    private LocalDateTime dob;

    private Boolean gender;

    private LocalDateTime joinedDate;

    private Long roleId;

}
