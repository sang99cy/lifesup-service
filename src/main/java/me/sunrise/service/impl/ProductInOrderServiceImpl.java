package me.sunrise.service.impl;

import me.sunrise.dto.DetailOrderDTO;
import me.sunrise.dto.OrderDTO;
import me.sunrise.entity.OrderMainEntity;
import me.sunrise.entity.ProductEnity;
import me.sunrise.entity.ProductInOrderEntity;
import me.sunrise.entity.UserEntity;
import me.sunrise.enums.ResultEnum;
import me.sunrise.exception.MyException;
import me.sunrise.repository.OrderRepository;
import me.sunrise.repository.ProductInOrderRepository;
import me.sunrise.repository.ProductInfoRepository;
import me.sunrise.service.ProductInOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductInOrderServiceImpl extends BaseService implements ProductInOrderService {

    @Autowired
    ProductInOrderRepository productInOrderRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductInfoRepository productInfoRepository;

    @Override
    @Transactional
    public void update(String itemId, Integer quantity, UserEntity UserEntity) {
//        Optional<ProductInOrderEntity> op = UserEntity.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
//        op.ifPresent(productInOrder -> {
//            productInOrder.setCount(quantity);
//            productInOrderRepository.save(productInOrder);
//        });

    }

    @Override
    public ProductInOrderEntity findOne(String itemId, UserEntity UserEntity) {
//        Optional<ProductInOrderEntity> op = UserEntity.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
        AtomicReference<ProductInOrderEntity> res = new AtomicReference<>();
//        op.ifPresent(res::set);
        return res.get();
    }

    @Override
    public void saveProductInOrder(List<ProductInOrderEntity> productInOrder) {
        for (ProductInOrderEntity entity : productInOrder) {
            productInOrderRepository.save(entity);
        }
    }

    @Override
    @Transactional
    public OrderMainEntity checkout(OrderDTO orderDTO) {
        OrderMainEntity order = super.map(orderDTO, OrderMainEntity.class);
        OrderMainEntity orderMainEntity = orderRepository.save(order);
        if (orderMainEntity.getOrderId() != null) {
            // luu detail order
            for (DetailOrderDTO detailOrderDTO : orderDTO.getDetailOrders()) {
                detailOrderDTO.setOrderId(orderMainEntity.getOrderId());
                /*check so luong sản phẩm mua*/
                checkSoluongMuavaGiamSLSP(detailOrderDTO.getProductId(),detailOrderDTO.getCount());
                /*nếu hợp lệ thì giảm số lượng sản phẩm*/
                /*nếu ko hợp lệ thì sẽ ko cho thanh toán thành công*/
                productInOrderRepository.save(super.map(detailOrderDTO, ProductInOrderEntity.class));
            }
        }
        return orderMainEntity;
    }

    public void checkSoluongMuavaGiamSLSP(String productId, Integer soluongMua) {
        ProductEnity productEnity = productInfoRepository.findByProductId(productId);
        Integer soLuongtrongKho = productEnity.getProductStock();
        if (soluongMua <= soLuongtrongKho) {
            productEnity.setProductStock((soLuongtrongKho - soluongMua));
            productInfoRepository.save(productEnity);
        } else {
            throw new MyException(ResultEnum.PRODUCT_NOT_ENOUGH);
        }
    }
}
