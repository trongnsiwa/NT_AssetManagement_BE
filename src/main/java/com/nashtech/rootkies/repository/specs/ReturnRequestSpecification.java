package com.nashtech.rootkies.repository.specs;

import com.nashtech.rootkies.model.Asset;
import com.nashtech.rootkies.model.Assignment;
import com.nashtech.rootkies.model.ReturnRequest;
import com.nashtech.rootkies.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.LocalDateTime;

public final class ReturnRequestSpecification {

    public ReturnRequestSpecification(){}

    public static Specification<ReturnRequest> isNotDeleted() {
        return (root, query, cb) -> cb.isFalse(root.<Boolean>get("isDeleted"));
    }

    public static Specification<ReturnRequest> hasRequestBy(User requestBy) {
        return (root, query, cb) -> cb.equal(root.<User>get("requestBy"), requestBy);
    }

    public static Specification<ReturnRequest> hasAssignment(Assignment assignment) {
        return (root, query, cb) -> cb.equal(root.<User>get("assignment"), assignment);
    }

    public static Specification<ReturnRequest> hasState(Boolean state) {
        return (root, query, cb) -> cb.equal(root.<Boolean>get("state"), state);
    }

    public static Specification<ReturnRequest> hasReturnedDate(LocalDateTime date){
        return (root, query, cb) -> cb.equal(root.get("returnedDate"), date);
    }

    public static Specification<ReturnRequest> nameContainsIgnoreCase(String searchTerm) {
        return (root, query, cb) -> {
            String containsLikePattern = getContainsLikePattern(searchTerm);
            Join<ReturnRequest, Asset> assetJoin = root.join("asset");
            Join<ReturnRequest, User> userJoin = root.join("requestBy");
            return cb.or(cb.like(cb.lower(assetJoin.<String>get("name")), containsLikePattern),
                    cb.like(cb.lower(assetJoin.<String>get("code")), containsLikePattern),
                    cb.like(cb.lower(userJoin.<String>get("username")), containsLikePattern));
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
