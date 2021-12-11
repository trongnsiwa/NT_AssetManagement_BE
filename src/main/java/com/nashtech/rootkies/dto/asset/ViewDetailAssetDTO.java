package com.nashtech.rootkies.dto.asset;

import com.nashtech.rootkies.dto.HistoricalAssigmentDTO.HistoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewDetailAssetDTO {

    private Long id;

    private String code;

    private String name;

    private String categoryName;

    private String stateName;

    private LocalDateTime installedDate;

    private String locationName;

    private String specification;

    private List<HistoryDTO> history;

}
