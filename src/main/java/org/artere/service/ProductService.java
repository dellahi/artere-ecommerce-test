package org.artere.service;

import org.artere.dto.ProductRequest;
import org.artere.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> findAll();

    ProductResponse findById(Long id);

    List<ProductResponse> findByCategoryId(Long categoryId);

    ProductResponse create(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);

    ProductResponse assignToCategory(Long productId, Long categoryId);

    ProductResponse removeFromCategory(Long productId);
}
