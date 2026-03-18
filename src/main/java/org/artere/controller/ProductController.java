package org.artere.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.artere.dto.ProductRequest;
import org.artere.dto.ProductResponse;
import org.artere.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Operations for managing products and their category assignments")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all products")
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Assign a product to a category.
     * PUT /api/products/{productId}/category/{categoryId}
     */
    @PutMapping("/{productId}/category/{categoryId}")
    @Operation(summary = "Assign a product to a category")
    public ProductResponse assignToCategory(@PathVariable Long productId, @PathVariable Long categoryId) {
        return productService.assignToCategory(productId, categoryId);
    }

    /**
     * Remove a product from its category.
     * DELETE /api/products/{productId}/category
     */
    @DeleteMapping("/{productId}/category")
    @Operation(summary = "Remove a product from its category")
    public ProductResponse removeFromCategory(@PathVariable Long productId) {
        return productService.removeFromCategory(productId);
    }
}
