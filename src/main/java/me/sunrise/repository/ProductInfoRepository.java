package me.sunrise.repository;

import me.sunrise.entity.ProductEnity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductEnity, String>,ProductInfoRepositoryCustom {
    ProductEnity findByProductId(String id);
    // onsale product
    Page<ProductEnity> findAllByProductStatusOrderByProductIdAsc(Integer productStatus, Pageable pageable);

    Page<ProductEnity> findAllByProductNameContaining(String productName, Pageable pageable);
    Page<ProductEnity> findAllByCategoryType(Integer categoryType, Pageable pageable);

    // product in one category
    Page<ProductEnity> findAllByCategoryTypeOrderByProductIdAsc(Integer categoryType, Pageable pageable);

    Page<ProductEnity> findAllByOrderByProductId(Pageable pageable);
    @Query("SELECT m FROM ProductEnity m WHERE m.productPrice < 25  ")
    Page<ProductEnity>under25(Pageable pageable);
    @Query("SELECT m FROM ProductEnity m WHERE m.productPrice >= 25 and m.productPrice < 50  ")
    Page<ProductEnity>from25to50(Pageable pageable);
    @Query("SELECT m FROM ProductEnity m WHERE m.productPrice >= 50 and m.productPrice < 100 ")
    Page<ProductEnity>from50to100(Pageable pageable);
    @Query("SELECT m FROM ProductEnity m WHERE m.productPrice >= 100 and m.productPrice < 200  ")
    Page<ProductEnity>from100to200(Pageable pageable);
    @Query("SELECT m FROM ProductEnity m WHERE m.productPrice > 200  ")
    Page<ProductEnity>above200(Pageable pageable);
}
