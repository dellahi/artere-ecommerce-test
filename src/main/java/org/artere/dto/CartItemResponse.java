package org.artere.dto;

public record CartItemResponse(
        Long id,
        Long productId,
        String productName,
        Double productPrice,
        Integer quantity,
        Double subtotal
) {
}