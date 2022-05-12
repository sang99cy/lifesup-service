package me.sunrise.service;

import me.sunrise.entity.Cart;
import me.sunrise.entity.ProductInOrderEntity;
import me.sunrise.entity.UserEntity;

import java.util.Collection;

public interface CartService {
    Cart getCart(UserEntity user);

    void mergeLocalCart(Collection<ProductInOrderEntity> productInOrders, UserEntity user);

    void delete(String itemId, UserEntity user);

    void checkout(UserEntity user);
}
