package org.artere.repository;

import org.artere.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Find all root categories (categories with no parent).
     */
    List<Category> findByParentIsNull();

    /**
     * Find all sub-categories of a given parent.
     */
    List<Category> findByParentId(Long parentId);
}

