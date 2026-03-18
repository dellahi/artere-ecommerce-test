package org.artere.dto;

public record ProductResponse(
        Long id,
        String name,
        Double price,
        Integer quantity,
        Long categoryId
) {
}

