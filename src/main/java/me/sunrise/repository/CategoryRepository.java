package me.sunrise.repository;

import me.sunrise.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    // Some category
    List<CategoryEntity> findByCategoryTypeInOrderByCategoryTypeAsc(List<Integer> categoryTypes);
    // All category
//    List<ProductCategory> findAllByOrderByCategoryType();
    // One category
    CategoryEntity findByCategoryType(Integer categoryType);
    Page<CategoryEntity> findAllByOrderByCategoryType(Pageable pageable);
    CategoryEntity findByCategoryId(Integer id);

    Page<CategoryEntity> findByCategoryNameContaining(String keySearch, Pageable pageable);
}
