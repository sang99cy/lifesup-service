package me.sunrise.repository;

import me.sunrise.entity.ProductInOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInOrderRepository extends JpaRepository<ProductInOrderEntity, Long> {
    List<ProductInOrderEntity> findByOrderId(Long orderId);
}
