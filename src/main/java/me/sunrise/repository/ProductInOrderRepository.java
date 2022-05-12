package me.sunrise.repository;

import me.sunrise.entity.ProductInOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrderEntity, Long> {

}
