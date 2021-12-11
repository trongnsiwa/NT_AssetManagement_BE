package com.nashtech.rootkies.repository;

import com.nashtech.rootkies.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset>{

    @Transactional
    @Modifying
    @Query(value = "update assets set is_deleted = true where id = ?1", nativeQuery = true)
    int disableAsset(Long id);

    @Query(value = "select max(a.asset_code) from assets a where a.asset_code like ?1%", nativeQuery = true)
    String findMaxAssetCode(String code);

    Asset findByName(String name);

    Asset findByCode(String code);

    @Query(value = "Select COUNT(id) from assets where category_id = ?1 and is_deleted = false and location_id = ?2", nativeQuery = true)
    int getAmountAssetByCategory(int category, Long locationID);

    @Query(value = "Select COUNT(id) from assets where category_id = ?1 and state = 1 and is_deleted = false and location_id = ?2", nativeQuery = true)
    int getAmountAssetByCategoryAndStateIsAssigned(int category, Long locationID);

    @Query(value = "Select COUNT(id) from assets where category_id = ?1 and state = 2 and is_deleted = false and location_id = ?2", nativeQuery = true)
    int getAmountAssetByCategoryAndStateIsAvailable(int category, Long locationID);

    @Query(value = "Select COUNT(id) from assets where category_id = ?1 and state = 3 and is_deleted = false and location_id = ?2", nativeQuery = true)
    int getAmountAssetByCategoryAndStateIsNotAvailable(int category, Long locationID);

    @Query(value = "Select COUNT(id) from assets where category_id = ?1 and state = 4 and is_deleted = false and location_id = ?2", nativeQuery = true)
    int getAmountAssetByCategoryAndStateIsWaitingForRecycling(int category, Long locationID);

    @Query(value = "Select COUNT(id) from assets where category_id = ?1 and state = 5 and is_deleted = false and location_id = ?2", nativeQuery = true)
    int getAmountAssetByCategoryAndStateIsRecycled(int category, Long locationID);

    @Query(value = "Select COUNT(id) from assets where category_id = ?1 and state = 6 and is_deleted = false and location_id = ?2", nativeQuery = true)
    int getAmountAssetByCategoryAndStateIsWaitingForAssign(int category, Long locationID);

}
