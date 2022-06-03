package me.sunrise.service.impl;


import me.sunrise.dto.ProductDTO;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    CategoryService categoryService;
    public final static String url = System.getProperty("user.dir");
    private final String UPLOAD_CONFIG = "upload-config.properties";

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
    public Page<ProductEnity> findAll(String productName, Pageable pageable) {
        if (productName == null) {
            return productInfoRepository.findAll(pageable);
        }
        return productInfoRepository.findAllByProductNameContaining(productName, pageable);
    }

    public Page<ProductEnity> findAllPageCategory(Integer categoryType, Pageable pageable){
        return productInfoRepository.findAllByCategoryType(categoryType, pageable);
    }

    @Override
    public Page<ProductEnity> findAllInCategory(Integer categoryType, Pageable pageable) {
        return productInfoRepository.findAllByCategoryTypeOrderByProductIdAsc(categoryType, pageable);
    }

    @Override
    public List<ProductEnity> findAll() {
        List<ProductEnity> products = productInfoRepository.findAllByOrderByCreateTimeAsc();
        return products;
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
        productInfo.setProductStatus(1);
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

    @Override
    public List<ProductDTO> doSearchProduct(ProductDTO keySearch) {
        return productInfoRepository.doSearchProduct(keySearch);
    }

    @Override
    public String uploadProduct(MultipartFile image) {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(UPLOAD_CONFIG));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String pathFile = properties.getProperty("uploadServer");
        String fileName = image.getOriginalFilename();
        File mkDir = new File(pathFile);
        if (!mkDir.exists()) {
            mkDir.mkdirs();
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File newFile = new File(pathFile, fileName);
            inputStream = image.getInputStream();
            outputStream = new FileOutputStream(newFile);

            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes, 0, 1024)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }
}
