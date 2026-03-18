package org.artere.mapper;

import org.artere.dto.CartItemResponse;
import org.artere.dto.CartResponse;
import org.artere.model.Cart;
import org.artere.model.CartItem;

import java.util.List;

public final class CartMapper {

    private CartMapper() {
    }

    public static CartResponse toResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems() != null
                ? cart.getItems().stream().map(CartMapper::toItemResponse).toList()
                : List.of();

        return new CartResponse(
                cart.getId(),
                cart.getCreatedAt(),
                items,
                cart.getTotalPrice()
        );
    }

    public static CartItemResponse toItemResponse(CartItem item) {
        return new CartItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getPrice(),
                item.getQuantity(),
                item.getSubtotal()
        );
    }
}