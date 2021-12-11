package com.nashtech.rootkies.dto.asset;

import com.nashtech.rootkies.dto.state.AssetStateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewAssetDTO {

    private Long id;

    private String code;

    private String name;

    private String categoryName;

    private AssetStateDTO state;

}
