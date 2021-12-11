package com.nashtech.rootkies.repository;

import com.nashtech.rootkies.enums.EAssetState;
import com.nashtech.rootkies.model.AssetState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetStateRepository extends JpaRepository<AssetState, Long> {

    Optional<AssetState> findByName(EAssetState name);

}
