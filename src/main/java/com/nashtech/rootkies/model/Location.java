package com.nashtech.rootkies.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(
        name = "locations"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

}
