package com.nashtech.rootkies.model;

import com.nashtech.rootkies.enums.EAssetState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "asset_states")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssetState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EAssetState name;

}
