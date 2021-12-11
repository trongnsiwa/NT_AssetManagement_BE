package com.nashtech.rootkies.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "assets", uniqueConstraints = {
        @UniqueConstraint(columnNames = "asset_code")
})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "asset_code")
    private String code;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String specification;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state")
    private AssetState state;

    @Column(name = "installed_date")
    private LocalDateTime installedDate;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managed_by")
    private User managedBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "asset")
    private List<Assignment> assignments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "asset")
    private List<ReturnRequest> returnRequests = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "asset")
    private List<HistoricalAssignment> historicalAssignments = new ArrayList<>();

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return Objects.equals(id, asset.id) && Objects.equals(code, asset.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
}
