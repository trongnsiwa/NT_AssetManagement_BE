package com.nashtech.rootkies.dto.returnrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReturnRequestForUserDTO {

    @NotNull
    private Long assignmentId;

    @NotNull
    private String requestBy;

}
