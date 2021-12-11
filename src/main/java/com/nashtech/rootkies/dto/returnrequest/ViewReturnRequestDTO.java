package com.nashtech.rootkies.dto.returnrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewReturnRequestDTO {

    private Long id;
    private String assetCode;
    private String assetName;
    private String requestBy;
    private LocalDateTime assignedDate;
    private String acceptedBy;
    private LocalDateTime returnedDate;
    private Boolean state;

}
