package com.nashtech.rootkies.payload.response;

import com.nashtech.rootkies.model.Location;
import com.nashtech.rootkies.model.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String code;
    private String username;
    private String fullname;
    private Role role;
    private Location location;
    private boolean isDeleted;
    private boolean isNew;

    public JwtResponse(String token, String username, String fullname, Role role, boolean isDeleted, boolean isNew) {
        this.token = token;
        this.username = username;
        this.fullname = fullname;
        this.role = role;
        this.isDeleted = isDeleted;
        this.isNew = isNew;
    }

    public JwtResponse(String token, String code, String username, String fullname, Role role, Location location, boolean isDeleted, boolean isNew) {
        this.token = token;
        this.code = code;
        this.username = username;
        this.fullname = fullname;
        this.role = role;
        this.location = location;
        this.isDeleted = isDeleted;
        this.isNew = isNew;
    }

}