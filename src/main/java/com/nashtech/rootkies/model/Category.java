package com.nashtech.rootkies.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories",
        indexes = {
                @Index(name = "category_idx", columnList = "id , name")
        }
)
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, length = 36)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Asset> assets = new ArrayList<>();

    @Column(name = "prefix", unique = true, nullable = false)
    private String prefix;

}
