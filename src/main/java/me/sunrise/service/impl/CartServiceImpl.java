package me.sunrise.service.impl;


import me.sunrise.entity.Cart;
import me.sunrise.entity.OrderMainEntity;
import me.sunrise.entity.ProductInOrderEntity;
import me.sunrise.entity.UserEntity;
import me.sunrise.repository.CartRepository;
import me.sunrise.repository.OrderRepository;
import me.sunrise.repository.ProductInOrderRepository;
import me.sunrise.repository.UserRepository;
import me.sunrise.service.CartService;
import me.sunrise.service.ProductService;
import me.sunrise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductService productService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductInOrderRepository productInOrderRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserService userService;

    @Override
    public Cart getCart(UserEntity user) {
        return user.getCart();
    }

    @Override
    @Transactional
    public void mergeLocalCart(Collection<ProductInOrderEntity> productInOrders, UserEntity user) {
        Cart finalCart = user.getCart();
        productInOrders.forEach(productInOrder -> {
//            Set<ProductInOrderEntity> set = finalCart.getProducts();
//            Optional<ProductInOrderEntity> old = set.stream().filter(e -> e.getProductId().equals(productInOrder.getProductId())).findFirst();
            ProductInOrderEntity prod = null;
//            if (old.isPresent()) {
//                prod = old.get();
//                prod.setCount(productInOrder.getCount() + prod.getCount());
//            } else {
//                prod = productInOrder;
//                prod.setCart(finalCart);
//                finalCart.getProducts().add(prod);
//            }
            productInOrderRepository.save(prod);
        });
        cartRepository.save(finalCart);

    }

    @Override
    @Transactional
    public void delete(String itemId, UserEntity user) {
//        Optional<ProductInOrderEntity> op = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
//        op.ifPresent(productInOrder -> {
////            productInOrder.setCart(null);
//            productInOrderRepository.deleteById(productInOrder.getId());
//        });
    }

    @Override
    @Transactional
    public void checkout(UserEntity user) {
        // Creat an order
        OrderMainEntity order = new OrderMainEntity(user);
        orderRepository.save(order);

        // clear cart's foreign key & set order's foreign key& decrease stock
//        user.getCart().getProducts().forEach(productInOrder -> {
////            productInOrder.setCart(null);
//            //productInOrder.setOrderMain(order);
//            productService.decreaseStock(productInOrder.getProductId(), productInOrder.getCount());
//            productInOrderRepository.save(productInOrder);
//        });
    }
}
