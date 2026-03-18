package org.artere.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.artere.dto.AddToCartRequest;
import org.artere.dto.CartResponse;
import org.artere.dto.UpdateCartItemRequest;
import org.artere.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@Tag(name = "Cart", description = "Operations for managing shopping carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @Operation(summary = "Create a new empty cart")
    public ResponseEntity<CartResponse> createCart() {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.createCart());
    }

    @GetMapping("/{cartId}")
    @Operation(summary = "Get a cart with its items and total price")
    public CartResponse getCart(@PathVariable Long cartId) {
        return cartService.getCart(cartId);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Add a product to the cart (increments quantity if already present)")
    public CartResponse addItem(@PathVariable Long cartId, @Valid @RequestBody AddToCartRequest request) {
        return cartService.addItem(cartId, request);
    }

    @PutMapping("/{cartId}/items/{itemId}")
    @Operation(summary = "Update the quantity of a cart item")
    public CartResponse updateItem(@PathVariable Long cartId,
                                   @PathVariable Long itemId,
                                   @Valid @RequestBody UpdateCartItemRequest request) {
        return cartService.updateItem(cartId, itemId, request);
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    @Operation(summary = "Remove an item from the cart")
    public ResponseEntity<Void> removeItem(@PathVariable Long cartId, @PathVariable Long itemId) {
        cartService.removeItem(cartId, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}")
    @Operation(summary = "Delete an entire cart")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }
}