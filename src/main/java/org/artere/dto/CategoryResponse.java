package org.artere.dto;

import java.util.List;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        Long parentId,
        List<CategoryResponse> subCategories,
        List<ProductResponse> products
) {
}

