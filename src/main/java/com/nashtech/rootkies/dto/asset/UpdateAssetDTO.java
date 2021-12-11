package com.nashtech.rootkies.dto.asset;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateAssetDTO {

    private Long id;

    private String name;

    private Long categoryId;

    private String specification;

    private Long stateId;

    private LocalDateTime installedDate;

}
