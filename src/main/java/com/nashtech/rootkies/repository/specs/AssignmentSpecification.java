package com.nashtech.rootkies.repository.specs;

import com.nashtech.rootkies.enums.EAssetState;
import com.nashtech.rootkies.enums.EAssignmentState;
import com.nashtech.rootkies.model.Asset;
import com.nashtech.rootkies.model.AssetState;
import com.nashtech.rootkies.model.Assignment;
import com.nashtech.rootkies.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.LocalDateTime;

public final class AssignmentSpecification {

    public AssignmentSpecification() {
    }

    public static Specification<Assignment> isNotDeleted() {
        return (root, query, cb) -> cb.isFalse(root.<Boolean>get("isDeleted"));
    }

    public static Specification<Assignment> hasUser(User user) {
        return (root, query, cb) -> cb.equal(root.<User>get("assignedTo"), user);
    }

    public static Specification<Assignment> hasAsset(Asset asset) {
        return (root, query, cb) -> cb.equal(root.<Asset>get("asset"), asset);
    }

  public static Specification<Assignment> assignmentHasAssignedDate(LocalDateTime date) {
    return (root, query, cb) -> cb.equal(root.<Assignment>get("assignedDate"), date);
  }

  public static Specification<Assignment> assignmentStateHasName(EAssignmentState name) {
    return (root, query, cb) -> {
      Join<Assignment, EAssignmentState> proState = root.join("state");
      return cb.equal(proState.<Assignment>get("name"), name);
    };
  }

  public static Specification<Assignment> assignmentStateNotHasName(EAssignmentState name) {
    return (root, query, cb) -> {
      Join<Assignment, EAssignmentState> proState = root.join("state");
      return cb.notEqual(proState.<Assignment>get("name"), name);
    };
  }

  public static Specification<Assignment> assignedThanCurrentDate(LocalDateTime currentDate) {
      return (root, query, cb) -> {
        return cb.lessThanOrEqualTo(root.<LocalDateTime>get("assignedDate"), currentDate);
      };
  }

  public static Specification<Assignment> nameContainsIgnoreCaseAssignment(String searchTerm) {
    return (root, query, cb) -> {
      String containsLikePattern = getContainsLikePattern(searchTerm);
      Join<Assignment, Asset> assetJoin = root.join("asset");
      Join<Assignment, User> userJoin = root.join("assignedTo");
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
