package org.artere.service.impl;

import org.artere.dto.AddToCartRequest;
import org.artere.dto.CartResponse;
import org.artere.dto.UpdateCartItemRequest;
import org.artere.exception.ResourceNotFoundException;
import org.artere.mapper.CartMapper;
import org.artere.model.Cart;
import org.artere.model.CartItem;
import org.artere.model.Product;
import org.artere.repository.CartItemRepository;
import org.artere.repository.CartRepository;
import org.artere.repository.ProductRepository;
import org.artere.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartResponse createCart() {
        Cart cart = new Cart();
        return CartMapper.toResponse(cartRepository.save(cart));
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(Long cartId) {
        return CartMapper.toResponse(findCartById(cartId));
    }

    @Override
    public CartResponse addItem(Long cartId, AddToCartRequest request) {
        Cart cart = findCartById(cartId);
        Product product = findProductById(request.productId());

        // If product already in cart, increment quantity
        Optional<CartItem> existing = cartItemRepository.findByCartIdAndProductId(cartId, product.getId());
        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + request.quantity());
        } else {
            CartItem item = new CartItem(product, request.quantity());
            cart.addItem(item);
        }

        return CartMapper.toResponse(cartRepository.save(cart));
    }

    @Override
    public CartResponse updateItem(Long cartId, Long itemId, UpdateCartItemRequest request) {
        Cart cart = findCartById(cartId);
        CartItem item = findCartItem(cart, itemId);

        item.setQuantity(request.quantity());

        return CartMapper.toResponse(cartRepository.save(cart));
    }

    @Override
    public CartResponse removeItem(Long cartId, Long itemId) {
        Cart cart = findCartById(cartId);
        CartItem item = findCartItem(cart, itemId);

        cart.removeItem(item);

        return CartMapper.toResponse(cartRepository.save(cart));
    }

    @Override
    public void clearCart(Long cartId) {
        Cart cart = findCartById(cartId);
        cartRepository.delete(cart);
    }

    private Cart findCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

    private CartItem findCartItem(Cart cart, Long itemId) {
        return cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));
    }
}