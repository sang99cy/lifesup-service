package me.sunrise.service;

import me.sunrise.entity.ProductInOrderEntity;
import me.sunrise.entity.UserEntity;

import java.util.List;

public interface ProductInOrderService {
    void update(String itemId, Integer quantity, UserEntity UserEntity);
    ProductInOrderEntity findOne(String itemId, UserEntity UserEntity);
    void saveProductInOrder(List<ProductInOrderEntity> productInOrders);
}
