package me.sunrise.service;

import me.sunrise.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

//    List<ProductCategory> findAll();
//
Page<CategoryEntity> findAll(Pageable pageable);


    CategoryEntity findOne(Integer categoryId);

    CategoryEntity findByCategoryType(Integer categoryType);

    List<CategoryEntity> findByCategoryTypeIn(List<Integer> categoryTypeList);

    CategoryEntity update(CategoryEntity category);

    CategoryEntity save(CategoryEntity category);

    void delete(Integer categoryId);

    List<CategoryEntity> listAllCategory();

}
