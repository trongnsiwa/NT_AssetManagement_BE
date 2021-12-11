package com.nashtech.rootkies.repository.specs;

import com.nashtech.rootkies.enums.EAssetState;
import com.nashtech.rootkies.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public final class AssetSpecification {

    public AssetSpecification(){}

    public static Specification<Asset> isNotDeleted() {
        return (root, query, cb) -> cb.isFalse(root.<Boolean>get("isDeleted"));
    }

    public static Specification<Asset> hasLocation(Location location) {
        return (root, query, cb) -> cb.equal(root.<Asset>get("location"), location);
    }

    public static Specification<Asset> categoryHasName(String name) {
        return (root, query, cb) -> {
            Join<Asset, Category> proCate = root.join("category");
            return cb.equal(proCate.<Category>get("name"), name);
        };
    }

    public static Specification<Asset> stateHasName(EAssetState name) {
        return (root, query, cb) -> {
            Join<Asset, AssetState> proState = root.join("state");
            return cb.equal(proState.<AssetState>get("name"), name);
        };
    }

    public static Specification<Asset> nameContainsIgnoreCase(String searchTerm) {
        return (root, query, cb) -> {
            String containsLikePattern = getContainsLikePattern(searchTerm);
            return cb.or(cb.like(cb.lower(root.<String>get("name")), containsLikePattern),
                    cb.like(cb.lower(root.<String>get("code")), containsLikePattern));
        };
    }

    private static String getContainsLikePattern(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "%";
        } else {
            return "%" + searchTerm.toLowerCase() + "%";
        }
    }
}
