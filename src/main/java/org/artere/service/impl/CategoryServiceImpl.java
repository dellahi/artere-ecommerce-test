package org.artere.service.impl;

import org.artere.dto.CategoryRequest;
import org.artere.dto.CategoryResponse;
import org.artere.exception.ResourceNotFoundException;
import org.artere.mapper.CategoryMapper;
import org.artere.model.Category;
import org.artere.repository.CategoryRepository;
import org.artere.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findRootCategories() {
        return categoryRepository.findByParentIsNull().stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        return CategoryMapper.toResponse(findEntityById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findSubCategories(Long parentId) {
        findEntityById(parentId);
        return categoryRepository.findByParentId(parentId).stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        Category category = CategoryMapper.toEntity(request);
        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category existing = findEntityById(id);
        CategoryMapper.updateEntity(existing, request);
        return CategoryMapper.toResponse(categoryRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        Category category = findEntityById(id);
        if (category.getParent() != null) {
            category.getParent().getSubCategories().remove(category);
        }
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse addSubCategory(Long parentId, Long childId) {
        Category parent = findEntityById(parentId);
        Category child = findEntityById(childId);

        if (parentId.equals(childId)) {
            throw new IllegalArgumentException("A category cannot be its own parent");
        }

        if (child.getParent() != null) {
            child.getParent().getSubCategories().remove(child);
        }

        parent.addSubCategory(child);
        return CategoryMapper.toResponse(categoryRepository.save(parent));
    }

    @Override
    public CategoryResponse removeSubCategory(Long parentId, Long childId) {
        Category parent = findEntityById(parentId);
        Category child = findEntityById(childId);

        if (child.getParent() == null || !child.getParent().getId().equals(parentId)) {
            throw new IllegalArgumentException(
                    "Category " + childId + " is not a sub-category of " + parentId);
        }

        parent.removeSubCategory(child);
        categoryRepository.save(child);
        return CategoryMapper.toResponse(categoryRepository.save(parent));
    }

    private Category findEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }
}
