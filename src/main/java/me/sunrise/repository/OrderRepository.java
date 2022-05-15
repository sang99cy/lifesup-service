package me.sunrise.repository;


import me.sunrise.entity.OrderMainEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderMainEntity, Long> {
    OrderMainEntity findByOrderId(Long orderId);


    Page<OrderMainEntity> findAllByOrderStatusOrderByCreateTimeDesc(Integer orderStatus, Pageable pageable);


    Page<OrderMainEntity> findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(String buyerEmail, Pageable pageable);

    Page<OrderMainEntity> findAllByOrderByOrderStatusAscCreateTimeDesc(Pageable pageable);

    Page<OrderMainEntity> findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(String buyerPhone, Pageable pageable);

    //    Page<OrderMain> findAllByOrderStatusOrderByCreateTimeDesc(Integer orderStatus, Pageable pageable);
    @Query("SELECT m FROM OrderMainEntity m WHERE m.orderStatus = 1 ")
    Page<OrderMainEntity> getOrderStatus1(Pageable pageable);

    @Query("SELECT m FROM OrderMainEntity m WHERE m.orderStatus = 2 ")
    Page<OrderMainEntity> getOrderStatus2(Pageable pageable);

    @Query("SELECT m FROM OrderMainEntity m WHERE m.orderStatus = 3 ")
    Page<OrderMainEntity> getOrderStatus3(Pageable pageable);

    @Query("SELECT m FROM OrderMainEntity m WHERE m.orderStatus = 0")
    Page<OrderMainEntity> getOrderStatus0(Pageable pageable);

    @Query("SELECT new map(count(m.orderId) as SOLUONG0) FROM OrderMainEntity m WHERE m.orderStatus = 0")
    Object[] getcountStatus0();
    @Query("SELECT new map(count(m.orderId) as SOLUONG1) FROM OrderMainEntity m WHERE m.orderStatus = 1")
    Object[] getcountStatus1();
    @Query("SELECT new map(count(m.orderId) as SOLUONG2) FROM OrderMainEntity m WHERE m.orderStatus = 2")
    Object[] getcountStatus2();
    @Query("SELECT new map(count(m.orderId) as SOLUONG3) FROM OrderMainEntity m WHERE m.orderStatus = 3")
    Object[] getcountStatus3();
    @Query("SELECT new map(count(m.orderId) as SOLUONG) FROM OrderMainEntity m ")
    Object[] getcountAll();
    @Query("SELECT new map(sum(m.orderAmount) as TONG0) FROM OrderMainEntity m WHERE m.orderStatus = 0")
    Object[] getsumStatus0();
    @Query("SELECT new map(sum(m.orderAmount) as TONG1) FROM OrderMainEntity m WHERE m.orderStatus = 1")
    Object[] getsumStatus1();
    @Query("SELECT new map(sum(m.orderAmount) as TONG2) FROM OrderMainEntity m WHERE m.orderStatus = 2")
    Object[] getsumStatus2();
    @Query("SELECT new map(sum(m.orderAmount) as TONG3) FROM OrderMainEntity m WHERE m.orderStatus = 3")
    Object[] getsumStatus3();
    @Query("SELECT new map(sum(m.orderAmount) as TONG) FROM OrderMainEntity m WHERE m.orderStatus <> 2 ")
    Object[] getsumAll();



}

