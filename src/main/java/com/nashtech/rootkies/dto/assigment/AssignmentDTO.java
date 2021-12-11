package com.nashtech.rootkies.dto.assigment;

import com.nashtech.rootkies.enums.EAssignmentState;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDTO {

    private Long id;

    private String assetCode;

    private String assetName;

    private String categoryName;

    private String assignTo;

    private String assignBy;

    private LocalDateTime assignedDate;

    private EAssignmentState state;

    private boolean isReturning;

}
