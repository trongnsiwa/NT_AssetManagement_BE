package com.nashtech.rootkies.repository;

import com.nashtech.rootkies.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String categoryName);

    Boolean existsByName(String categoryName);

    Boolean existsByPrefix(String prefix);

}
