package org.artere.service;

import org.artere.dto.CategoryRequest;
import org.artere.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> findAll();

    List<CategoryResponse> findRootCategories();

    CategoryResponse findById(Long id);

    List<CategoryResponse> findSubCategories(Long parentId);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Long id, CategoryRequest request);

    void delete(Long id);

    CategoryResponse addSubCategory(Long parentId, Long childId);

    CategoryResponse removeSubCategory(Long parentId, Long childId);
}
