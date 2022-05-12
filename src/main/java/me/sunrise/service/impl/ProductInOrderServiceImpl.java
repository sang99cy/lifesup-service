package me.sunrise.service.impl;

import me.sunrise.entity.ProductInOrderEntity;
import me.sunrise.entity.UserEntity;
import me.sunrise.repository.ProductInOrderRepository;
import me.sunrise.service.ProductInOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductInOrderServiceImpl implements ProductInOrderService {

    @Autowired
    ProductInOrderRepository productInOrderRepository;

    @Override
    @Transactional
    public void update(String itemId, Integer quantity, UserEntity UserEntity) {
        Optional<ProductInOrderEntity> op = UserEntity.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
        op.ifPresent(productInOrder -> {
            productInOrder.setCount(quantity);
            productInOrderRepository.save(productInOrder);
        });

    }

    @Override
    public ProductInOrderEntity findOne(String itemId, UserEntity UserEntity) {
        Optional<ProductInOrderEntity> op = UserEntity.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
        AtomicReference<ProductInOrderEntity> res = new AtomicReference<>();
        op.ifPresent(res::set);
        return res.get();
    }

    @Override
    public void saveProductInOrder(List<ProductInOrderEntity> productInOrder) {
        for (ProductInOrderEntity entity : productInOrder) {
            productInOrderRepository.save(entity);
        }
    }
}
