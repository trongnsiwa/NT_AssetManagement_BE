package com.nashtech.rootkies.model;

import com.nashtech.rootkies.model.generator.StringPrefixedSequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
        },
        indexes = {
                @Index(name = "idIndex", columnList = "code, username")
        })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_gr_user_code")
    @GenericGenerator(name = "sq_gr_user_code", strategy = "com.nashtech.rootkies.model" +
            ".generator.StringPrefixedSequenceGenerator", parameters = {
            @Parameter(name = StringPrefixedSequenceGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "SD"),
            @Parameter(name = StringPrefixedSequenceGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
    })
    private String code;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "last_name", length = 20)
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDateTime dob;

    private Boolean gender;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "joined_date")
    private LocalDateTime joinedDate;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "is_new")
    private boolean isNew;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignedTo")
    private List<Assignment> assignments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requestBy")
    private List<ReturnRequest> returnRequests = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignedBy")
    private List<Assignment> managedAssignments = new ArrayList<>();

}
