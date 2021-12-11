package com.nashtech.rootkies.dto.assigment;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateAssignmentDTO {
    @NotNull
    private String staffCode;

    @NotNull
    private Long assetId;

    @NotNull
    private LocalDateTime assignedDate;

    private String note;

    @NotNull
    private String assignBy;
}
