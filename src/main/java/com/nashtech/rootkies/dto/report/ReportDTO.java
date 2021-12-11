package com.nashtech.rootkies.dto.report;

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
public class ReportDTO {

    private String nameCategory;

    private int total;

    private int ammountAssigned;

    private int ammountAvailable;

    private int ammountNotAvailable;

    private int ammountWaitingForRecycling;

    private int ammountWaitingForAssign;

    private int ammountRecycled;

}
