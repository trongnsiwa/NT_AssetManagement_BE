package com.nashtech.rootkies.model;

import com.nashtech.rootkies.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles",
        indexes = {
                @Index(name = "type_idx", columnList = "id , name")
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }

}