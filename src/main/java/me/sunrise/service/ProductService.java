package me.sunrise.service;


import me.sunrise.entity.ProductEnity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {


    Page<ProductEnity>under25(Pageable pageable);

    Page<ProductEnity>from25to50(Pageable pageable);

    Page<ProductEnity>from50to100(Pageable pageable);

    Page<ProductEnity>from100to200(Pageable pageable);

    Page<ProductEnity>above200(Pageable pageable);

    ProductEnity findOne(String productId);

    // All selling products
    Page<ProductEnity> findUpAll(Pageable pageable);
    // All products
    Page<ProductEnity> findAll(Pageable pageable);
    // All products in a category
    Page<ProductEnity> findAllInCategory(Integer categoryType, Pageable pageable);

    List<ProductEnity> findAll();
    // increase stock
    void increaseStock(String productId, int amount);

    //decrease stock
    void decreaseStock(String productId, int amount);

    ProductEnity offSale(String productId);

    ProductEnity onSale(String productId);

    ProductEnity update(ProductEnity productInfo);
    
    ProductEnity save(ProductEnity productInfo);

    void delete(String productId);

    boolean changeStatus(String productId,Integer status);
}
