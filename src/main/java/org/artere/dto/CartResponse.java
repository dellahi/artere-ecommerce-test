package org.artere.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CartResponse(
        Long id,
        LocalDateTime createdAt,
        List<CartItemResponse> items,
        Double totalPrice
) {
}

