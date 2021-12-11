package com.nashtech.rootkies.dto.assigment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewAssignmentDTO {

    private Long id;

    private String assetCode;

    private String assetName;

    private String assignedTo;

    private String assignedBy;

    private LocalDateTime assignedDate;

    private String state;

    private boolean isReturning;

}
