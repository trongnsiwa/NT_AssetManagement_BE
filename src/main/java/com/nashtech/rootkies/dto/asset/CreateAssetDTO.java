package com.nashtech.rootkies.dto.asset;

import com.nashtech.rootkies.dto.state.CreateAssetStateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssetDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String categoryName;

    @NotBlank
    private String specification;

    @NotNull
    private LocalDateTime installedDate;

    @NotNull
    private String managedBy;

    @NotNull
    private CreateAssetStateDTO state;

    @NotNull
    private Long locationId;
}
