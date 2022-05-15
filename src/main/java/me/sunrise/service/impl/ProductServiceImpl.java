package me.sunrise.service.impl;


import me.sunrise.entity.ProductEnity;
import me.sunrise.enums.ProductStatusEnum;
import me.sunrise.enums.ResultEnum;
import me.sunrise.exception.MyException;
import me.sunrise.repository.ProductInfoRepository;
import me.sunrise.service.CategoryService;
import me.sunrise.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    CategoryService categoryService;


    @Override
    public Page<ProductEnity> under25(Pageable pageable) {
        return productInfoRepository.under25(pageable);
    }

    @Override
    public Page<ProductEnity> from25to50(Pageable pageable) {
        return productInfoRepository.from25to50(pageable);
    }

    @Override
    public Page<ProductEnity> from50to100(Pageable pageable) {
        return productInfoRepository.from50to100(pageable);
    }

    @Override
    public Page<ProductEnity> from100to200(Pageable pageable) {
        return productInfoRepository.from100to200(pageable);
    }

    @Override
    public Page<ProductEnity> above200(Pageable pageable) {
        return productInfoRepository.above200(pageable);
    }


    @Override
    public ProductEnity findOne(String productId) {

        ProductEnity productInfo = productInfoRepository.findByProductId(productId);
        return productInfo;
    }

    @Override
    public Page<ProductEnity> findUpAll(Pageable pageable) {
        return productInfoRepository.findAllByProductStatusOrderByProductIdAsc(ProductStatusEnum.UP.getCode(), pageable);
    }

    @Override
    public Page<ProductEnity> findAll(Pageable pageable) {
        return productInfoRepository.findAllByOrderByProductId(pageable);
    }

    @Override
    public Page<ProductEnity> findAllInCategory(Integer categoryType, Pageable pageable) {
        return productInfoRepository.findAllByCategoryTypeOrderByProductIdAsc(categoryType, pageable);
    }

    @Override
    public List<ProductEnity> findAll() {
        return productInfoRepository.findAll();
    }

    @Override
    @Transactional
    public void increaseStock(String productId, int amount) {
        ProductEnity productInfo = findOne(productId);
        if (productInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        int update = productInfo.getProductStock() + amount;
        productInfo.setProductStock(update);
        productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void decreaseStock(String productId, int amount) {
        ProductEnity productInfo = findOne(productId);
        if (productInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        int update = productInfo.getProductStock() - amount;
//        if(update <= 0) throw new MyException(ResultEnum.PRODUCT_NOT_ENOUGH );

        productInfo.setProductStock(update);
        productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public ProductEnity offSale(String productId) {
        ProductEnity productInfo = findOne(productId);
        if (productInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        if (productInfo.getProductStatus() == ProductStatusEnum.DOWN.getCode()) {
            throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
        }


        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public ProductEnity onSale(String productId) {
        ProductEnity productInfo = findOne(productId);
        if (productInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        if (productInfo.getProductStatus() == ProductStatusEnum.UP.getCode()) {
            throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
        }


        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductEnity update(ProductEnity productInfo) {

        // if null throw exception
//        categoryService.findByCategoryType(productInfo.getCategoryType());
//        if(productInfo.getProductStatus() > 1) {
//            throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
//        }
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductEnity save(ProductEnity productInfo) {
        return update(productInfo);
    }

    @Override
    public void delete(String productId) {
        ProductEnity productInfo = findOne(productId);
        if (productInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);
        productInfoRepository.delete(productInfo);

    }

    @Override
    public boolean changeStatus(String productId, Integer status) {
        ProductEnity productInfo = productInfoRepository.findByProductId(productId);
        if (productInfo != null) {
            productInfo.setProductStatus(status);
            productInfoRepository.save(productInfo);
            return true;
        }
        return false;
    }
}
