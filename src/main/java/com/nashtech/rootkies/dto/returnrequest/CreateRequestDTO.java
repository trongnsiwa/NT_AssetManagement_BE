package com.nashtech.rootkies.dto.returnrequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequestDTO {

    @NotNull
    private Long assignmentId;

    @NotBlank
    private String requestBy;

}
