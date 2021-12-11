package com.nashtech.rootkies.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePasswordUserDTO {

    private String code;

    private String oldPassword;

    private String password;

}
