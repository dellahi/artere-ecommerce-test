package org.artere.mapper;

import org.artere.dto.ProductRequest;
import org.artere.dto.ProductResponse;
import org.artere.model.Product;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory() != null ? product.getCategory().getId() : null
        );
    }

    public static Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        return product;
    }

    public static void updateEntity(Product product, ProductRequest request) {
        product.setName(request.name());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
    }
}

