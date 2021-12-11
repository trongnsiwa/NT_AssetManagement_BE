package com.nashtech.rootkies.dto.assigment;

import com.nashtech.rootkies.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewDetailAssignmentDTO {

    private Long assetId;

    private String assetCode;

    private String assetName;

    private String specification;

    private String assignedTo;

    private String assignedToFullName;

    private String assignedBy;

    private LocalDateTime assignedDate;

    private String state;

    private String note;

    private boolean isReturning;

}
