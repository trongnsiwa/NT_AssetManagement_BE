package com.nashtech.rootkies.dto.state;

import com.nashtech.rootkies.enums.EAssetState;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssetStateDTO {

    private EAssetState name;
}
