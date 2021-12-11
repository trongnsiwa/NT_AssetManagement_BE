package com.nashtech.rootkies.repository.specs;

import com.nashtech.rootkies.model.Location;
import com.nashtech.rootkies.model.Role;
import com.nashtech.rootkies.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Path;

public final class UserSpecification {

    public UserSpecification() {
    }

    public static Specification<User> isNotDeleted() {
        return (root, query, cb) -> cb.isFalse(root.<Boolean>get("isDeleted"));
    }

    public static Specification<User> hasType(Role role) {
        return (root, query, cb) -> cb.equal(root.<User>get("role"), role);
    }

    public static Specification<User> hasLocation(Location location) {
        return (root, query, cb) -> cb.equal(root.<User>get("location"), location);
    }

    public static Specification<User> nameContainsIgnoreCase(String searchTerm) {
        Specification<User> spec = null;
        String[] arr = searchTerm.split(" ");
        int length = arr.length;
        if (arr.length == 1) {
            if (searchTerm.substring(0, 2).equalsIgnoreCase("SD")) {
                spec = (root, query, cb) -> {
                    String containsLikePattern = getContainsLikePattern(searchTerm);
                    return cb.or(cb.like(cb.lower(root.<String>get("code")), containsLikePattern));
                };
            } else {
                spec = (root, query, cb) -> {
                    String containsLikePattern = getContainsLikePattern(searchTerm);
                    return cb.or(cb.like(cb.lower(root.<String>get("username")), containsLikePattern),
                            cb.like(cb.lower(root.<String>get("firstName")), containsLikePattern),
                            cb.like(cb.lower(root.<String>get("lastName")), containsLikePattern));
                };
            }
        } else if (arr.length > 1) {
            spec = (root, query, cb) -> {
                String containsLikePattern = getContainsLikePattern(arr[0]);
                String containsLikePattern2 = getContainsLikePattern(arr[1]);
                return cb.and(cb.like(cb.lower(root.<String>get("firstName")), containsLikePattern),
                        cb.like(cb.lower(root.<String>get("lastName")), containsLikePattern2));
            };
        }
        return spec;
    }

    private static String getContainsLikePattern(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "%";
        } else {
            return "%" + searchTerm.toLowerCase() + "%";
        }
    }


}
