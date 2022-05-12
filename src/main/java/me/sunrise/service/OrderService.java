package me.sunrise.service;

import me.sunrise.entity.OrderMainEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderMainEntity> findAll(Pageable pageable);


    Page<OrderMainEntity> findstatus1(Pageable pageable);

    Page<OrderMainEntity> findstatus2(Pageable pageable);

    Page<OrderMainEntity> findstatus3(Pageable pageable);


    Page<OrderMainEntity> findstatus0(Pageable pageable);


    Object[] getcountStautus0();

    Object[] getcountStautus1();

    Object[] getcountStautus2();

    Object[] getcountStautus3();

    Object[] getcountAll();

    Object[] getsumStautus0();

    Object[] getsumStautus1();

    Object[] getsumStautus2();

    Object[] getsumStautus3();

    Object[] getsumAll();

    abstract Page<OrderMainEntity> findByStatus(Integer status, Pageable pageable);

    Page<OrderMainEntity> findByBuyerEmail(String email, Pageable pageable);

    Page<OrderMainEntity> findByBuyerPhone(String phone, Pageable pageable);

    OrderMainEntity findOne(Long orderId);


    OrderMainEntity finish(Long orderId);

    OrderMainEntity approved(Long orderId);

    OrderMainEntity cancel(Long orderId);

//    Page<OrderMain> findByStatus1(Integer orderStatus);
}