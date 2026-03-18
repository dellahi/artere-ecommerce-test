package org.artere.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.artere.dto.CategoryRequest;
import org.artere.dto.CategoryResponse;
import org.artere.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Operations for managing categories and sub-categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Get all categories")
    public List<CategoryResponse> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/roots")
    @Operation(summary = "Get all root categories (no parent)")
    public List<CategoryResponse> findRootCategories() {
        return categoryService.findRootCategories();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a category by ID")
    public CategoryResponse findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/{id}/subcategories")
    @Operation(summary = "Get sub-categories of a category")
    public List<CategoryResponse> findSubCategories(@PathVariable Long id) {
        return categoryService.findSubCategories(id);
    }

    @PostMapping
    @Operation(summary = "Create a new category")
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category")
    public CategoryResponse update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{parentId}/subcategories/{childId}")
    @Operation(summary = "Link a child category under a parent category")
    public CategoryResponse addSubCategory(@PathVariable Long parentId, @PathVariable Long childId) {
        return categoryService.addSubCategory(parentId, childId);
    }

    @DeleteMapping("/{parentId}/subcategories/{childId}")
    @Operation(summary = "Unlink a child category from its parent (makes it a root)")
    public CategoryResponse removeSubCategory(@PathVariable Long parentId, @PathVariable Long childId) {
        return categoryService.removeSubCategory(parentId, childId);
    }
}
