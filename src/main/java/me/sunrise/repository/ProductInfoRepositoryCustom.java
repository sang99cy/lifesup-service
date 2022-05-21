package me.sunrise.repository;

import me.sunrise.dto.ProductDTO;

import java.util.List;

public interface ProductInfoRepositoryCustom {
    List<ProductDTO> doSearchProduct(ProductDTO product);
}
