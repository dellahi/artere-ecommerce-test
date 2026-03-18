package org.artere.mapper;

import org.artere.dto.CategoryRequest;
import org.artere.dto.CategoryResponse;
import org.artere.model.Category;

import java.util.List;

public final class CategoryMapper {

    private CategoryMapper() {
    }

    public static CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }

        List<CategoryResponse> subCategories = category.getSubCategories() != null
                ? category.getSubCategories().stream().map(CategoryMapper::toResponse).toList()
                : List.of();

        List<org.artere.dto.ProductResponse> products = category.getProducts() != null
                ? category.getProducts().stream().map(ProductMapper::toResponse).toList()
                : List.of();

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getParent() != null ? category.getParent().getId() : null,
                subCategories,
                products
        );
    }

    public static Category toEntity(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        category.setDescription(request.description());
        return category;
    }

    public static void updateEntity(Category category, CategoryRequest request) {
        category.setName(request.name());
        category.setDescription(request.description());
    }
}

