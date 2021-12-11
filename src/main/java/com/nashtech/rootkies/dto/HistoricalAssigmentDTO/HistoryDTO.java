package com.nashtech.rootkies.dto.HistoricalAssigmentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {

    private LocalDateTime assignedDate;

    private String assignedTo;

    private String assignedBy;

    private LocalDateTime returnedDate;

}
