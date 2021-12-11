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
public class UpdateAssigmentDTO {
    Long id;
    String username;
    Long prevAssetId;
    Long assetId;
    LocalDateTime assignDate;
    String note;
}
