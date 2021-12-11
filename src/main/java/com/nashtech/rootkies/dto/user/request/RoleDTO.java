package com.nashtech.rootkies.dto.user.request;

import com.nashtech.rootkies.enums.ERole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Long id;

    private ERole name;

}
