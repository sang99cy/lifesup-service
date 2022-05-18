package me.sunrise.service.impl;


import me.sunrise.entity.OrderMainEntity;
import me.sunrise.entity.ProductInOrderEntity;
import me.sunrise.entity.ProductEnity;
import me.sunrise.enums.OrderStatusEnum;
import me.sunrise.enums.ResultEnum;
import me.sunrise.exception.MyException;
import me.sunrise.repository.OrderRepository;
import me.sunrise.repository.ProductInOrderRepository;
import me.sunrise.repository.ProductInfoRepository;
import me.sunrise.repository.UserRepository;
import me.sunrise.service.OrderService;
import me.sunrise.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductInfoRepository productInfoRepository;
    @Autowired
    ProductService productService;
    @Autowired
    ProductInOrderRepository productInOrderRepository;

    @Override
    public Page<OrderMainEntity> findAll(Pageable pageable) {
        return orderRepository.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
    }

    @Override
    public Page<OrderMainEntity> findstatus1(Pageable pageable) {
        return orderRepository.getOrderStatus1(pageable);
    }

    @Override
    public Page<OrderMainEntity> findstatus2(Pageable pageable) {
        return orderRepository.getOrderStatus2(pageable);
    }

    @Override
    public Page<OrderMainEntity> findstatus3(Pageable pageable) {
        return orderRepository.getOrderStatus3(pageable);
    }

    @Override
    public Page<OrderMainEntity> findstatus0(Pageable pageable) {
        return orderRepository.getOrderStatus0(pageable);
    }

    @Override
    public Object[] getcountStautus0() {
        return orderRepository.getcountStatus0();
    }

    @Override
    public Object[] getcountStautus1() {
        return orderRepository.getcountStatus1();
    }

    @Override
    public Object[] getcountStautus2() {
        return orderRepository.getcountStatus2();
    }

    @Override
    public Object[] getcountStautus3() {
        return orderRepository.getcountStatus3();
    }

    @Override
    public Object[] getcountAll() {
        return orderRepository.getcountAll();
    }

    @Override
    public Object[] getsumStautus0() {
        return orderRepository.getsumStatus0();
    }

    @Override
    public Object[] getsumStautus1() {
        return orderRepository.getsumStatus1();
    }

    @Override
    public Object[] getsumStautus2() {
        return orderRepository.getsumStatus2();
    }

    @Override
    public Object[] getsumStautus3() {
        return orderRepository.getsumStatus3();
    }

    @Override
    public Object[] getsumAll() {
        return orderRepository.getsumAll();
    }


    @Override
    public Page<OrderMainEntity> findByStatus(Integer status, Pageable pageable) {
        return orderRepository.findAllByOrderStatusOrderByCreateTimeDesc(status, pageable);
    }

    //    @Override
//    public Page<OrderMain> findByStatus1(Integer orderStatus, Pageable pageable) {
//        return orderRepository.findAllByOrderStatusOrderByCreateTimeDesc(orderStatus, pageable);
//    }
    @Override
    public Page<OrderMainEntity> findByBuyerEmail(String email, Pageable pageable) {
        return orderRepository.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
    }

    @Override
    public Page<OrderMainEntity> findByBuyerPhone(String phone, Pageable pageable) {
        return orderRepository.findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(phone, pageable);
    }

    @Override
    public OrderMainEntity findOne(Long orderId) {
        OrderMainEntity orderMain = orderRepository.findByOrderId(orderId);
        if (orderMain == null) {
            throw new MyException(ResultEnum.ORDER_NOT_FOUND);
        }
        return orderMain;
    }

    @Override
    @Transactional
    public OrderMainEntity finish(Long orderId) {
        OrderMainEntity orderMain = findOne(orderId);
        if (!orderMain.getOrderStatus().equals(OrderStatusEnum.APPROVED.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderRepository.save(orderMain);
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public OrderMainEntity approved(Long orderId) {
        OrderMainEntity orderMain = findOne(orderId);
        if (!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.APPROVED.getCode());
        orderRepository.save(orderMain);
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public OrderMainEntity cancel(Long orderId) {
        OrderMainEntity orderMain = findOne(orderId);
        if (!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        orderRepository.save(orderMain);

        // Restore Stock
//        Iterable<ProductInOrderEntity> products = orderMain.getProducts();
//        for(ProductInOrderEntity productInOrder : products) {
//            ProductEnity productInfo = productInfoRepository.findByProductId(productInOrder.getProductId());
//            if(productInfo != null) {
//                productService.increaseStock(productInOrder.getProductId(), productInOrder.getCount());
//            }
//        }
        return orderRepository.findByOrderId(orderId);

    }


    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        List<ProductInOrderEntity> orderDetails = productInOrderRepository.findByOrderId(orderId);
        if (orderDetails.size() > 0) {
            for (ProductInOrderEntity orderDetail : orderDetails) {
                productInOrderRepository.delete(orderDetail);
            }
            OrderMainEntity orderOverview = orderRepository.findByOrderId(orderId);
            orderRepository.delete(orderOverview);
        }
    }

    @Override
    public List<ProductInOrderEntity> viewDetailOrder(Long orderId) {
        return productInOrderRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public boolean chuyenTinhTrangDonHang(Long orderId, Integer orderStatus) {
        ProductEnity product;
        List<ProductInOrderEntity> detailOrders;
        OrderMainEntity order = orderRepository.findByOrderId(orderId);
        if (order != null) {
            order.setOrderStatus(orderStatus);
            OrderMainEntity response = orderRepository.save(order);
            /*nếu status == 2 trường hợp hủy đơn hàng phải trả về số lượng về cho sản phẩm đó*/
            if (response.getOrderStatus() == 2) {
                detailOrders = productInOrderRepository.findByOrderId(orderId);
                for (ProductInOrderEntity item : detailOrders) {
                    /*cập nhật lại số lượng khi hủy đơn hàng*/
                    product = productInfoRepository.findByProductId(item.getProductId());
                    Integer soluong = product.getProductStock() + item.getCount();
                    product.setProductStock(soluong);
                    productInfoRepository.save(product);
                }
            }
            return true;
        }
        return false;
    }
}
