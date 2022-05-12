package me.sunrise.service.impl;


import me.sunrise.entity.CategoryEntity;
import me.sunrise.enums.ResultEnum;
import me.sunrise.exception.MyException;
import me.sunrise.repository.CategoryRepository;
import me.sunrise.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository productCategoryRepository;
    @Autowired
    CategoryService categoryService;

    @Override
    public Page<CategoryEntity> findAll(Pageable pageable) {
        return productCategoryRepository.findAllByOrderByCategoryType(pageable);
    }

    @Override
    public CategoryEntity findOne(Integer categoryId) {

        CategoryEntity category = productCategoryRepository.findByCategoryId(categoryId);
        return category;
    }


    @Override
    public CategoryEntity findByCategoryType(Integer categoryType) {
        CategoryEntity res = productCategoryRepository.findByCategoryType(categoryType);
        if(res == null) throw new MyException(ResultEnum.CATEGORY_NOT_FOUND);
        return res;
    }

    @Override
    public List<CategoryEntity> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        List<CategoryEntity> res = productCategoryRepository.findByCategoryTypeInOrderByCategoryTypeAsc(categoryTypeList);
       //res.sort(Comparator.comparing(ProductCategory::getCategoryType));
        return res;
    }

    @Override
    public CategoryEntity update(CategoryEntity category) {

        // if null throw exception
//        categoryService.findByCategoryType(category.getCategoryType());

        return productCategoryRepository.save(category);
    }

    @Override
    public CategoryEntity save(CategoryEntity category) {
        return update(category);
    }

    @Override
    public void delete(Integer categoryId) {
        CategoryEntity category = findOne(categoryId);
        if (category == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);
        productCategoryRepository.delete(category);

    }

    @Override
    public List<CategoryEntity> listAllCategory() {
        return productCategoryRepository.findAll();
    }


}
