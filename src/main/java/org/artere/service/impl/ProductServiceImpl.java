package org.artere.service.impl;

import org.artere.dto.ProductRequest;
import org.artere.dto.ProductResponse;
import org.artere.exception.ResourceNotFoundException;
import org.artere.mapper.ProductMapper;
import org.artere.model.Category;
import org.artere.model.Product;
import org.artere.repository.CategoryRepository;
import org.artere.repository.ProductRepository;
import org.artere.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        return ProductMapper.toResponse(findEntityById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = ProductMapper.toEntity(request);
        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product existing = findEntityById(id);
        ProductMapper.updateEntity(existing, request);
        return ProductMapper.toResponse(productRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        Product product = findEntityById(id);
        if (product.getCategory() != null) {
            product.getCategory().getProducts().remove(product);
        }
        productRepository.delete(product);
    }

    @Override
    public ProductResponse assignToCategory(Long productId, Long categoryId) {
        Product product = findEntityById(productId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        if (product.getCategory() != null) {
            product.getCategory().getProducts().remove(product);
        }

        category.addProduct(product);
        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse removeFromCategory(Long productId) {
        Product product = findEntityById(productId);

        if (product.getCategory() == null) {
            throw new IllegalArgumentException("Product " + productId + " is not assigned to any category");
        }

        product.getCategory().removeProduct(product);
        return ProductMapper.toResponse(productRepository.save(product));
    }

    private Product findEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
}
