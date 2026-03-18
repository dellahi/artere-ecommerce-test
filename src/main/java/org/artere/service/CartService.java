package org.artere.service;

import org.artere.dto.AddToCartRequest;
import org.artere.dto.CartResponse;
import org.artere.dto.UpdateCartItemRequest;

public interface CartService {

    CartResponse createCart();

    CartResponse getCart(Long cartId);

    CartResponse addItem(Long cartId, AddToCartRequest request);

    CartResponse updateItem(Long cartId, Long itemId, UpdateCartItemRequest request);

    CartResponse removeItem(Long cartId, Long itemId);

    void clearCart(Long cartId);
}

