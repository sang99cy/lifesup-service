package me.sunrise.Controller;


import me.sunrise.entity.CategoryEntity;
import me.sunrise.exception.ResourceNotFoundException;
import me.sunrise.repository.CategoryRepository;
import me.sunrise.vo.response.CategoryPage;
import me.sunrise.entity.ProductEnity;
import me.sunrise.service.CategoryService;
import me.sunrise.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductService productService;

    @GetMapping("/categoryList")
    public Page<CategoryEntity> orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<CategoryEntity> categoryPage;

        categoryPage = categoryService.findAll(request);

        return categoryPage;
    }

    @GetMapping("/categories")
    public List<CategoryEntity> orderList() {
        return categoryService.listAllCategory();
    }

    @GetMapping("/category")
    public Map<String, Object> doSearchCategory(@RequestParam(required = false) String keySearch,
                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "size", defaultValue = "3") int size) {
        List<CategoryEntity> categories = new ArrayList<>();
        Pageable paging = PageRequest.of(page - 1, size);
        Page<CategoryEntity> pageCategorys;
        if (keySearch == null)
            pageCategorys = categoryRepository.findAll(paging);
        else
            pageCategorys = categoryRepository.findByCategoryNameContaining(keySearch, paging);
        categories = pageCategorys.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("tutorials", categories);
        response.put("currentPage", pageCategorys.getNumber());
        response.put("totalItems", pageCategorys.getTotalElements());
        response.put("totalPages", pageCategorys.getTotalPages());
        return response;
    }

    /**
     * Show products in category
     *
     * @param categoryType
     * @param page
     * @param size
     * @return
     */

    @GetMapping("/category/{type}")
    public CategoryPage showOne(@PathVariable("type") Integer categoryType,
                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "size", defaultValue = "3") Integer size) {

        CategoryEntity cat = categoryService.findByCategoryType(categoryType);
        PageRequest request = PageRequest.of(page - 1, size);
        Page<ProductEnity> productInCategory = productService.findAllInCategory(categoryType, request);
        CategoryPage tmp = new CategoryPage("", productInCategory);
        tmp.setCategory(cat.getCategoryName());
        return tmp;
    }

    @PostMapping("/seller/category/new")
    public ResponseEntity<?> add(@RequestBody CategoryEntity category) {
        try {
            CategoryEntity returnedCategory = categoryService.save(category);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/seller/category/{id}/edit")
    public ResponseEntity<CategoryEntity> updateEmployee(@PathVariable(value = "id") Integer categoryId,
                                                         @Valid @RequestBody CategoryEntity employeeDetails) throws ResourceNotFoundException {
        CategoryEntity employee = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + categoryId));

        employee.setCategoryName(employeeDetails.getCategoryName());
//        employee.setCategoryType(employeeDetails.getCategoryType());
        final CategoryEntity updatedEmployee = categoryRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/category/{categoryId}")
    public CategoryEntity showOne(@PathVariable("categoryId") Integer categoryId) {

        CategoryEntity category = categoryService.findOne(categoryId);

//        // Product is not available
//        if (productInfo.getProductStatus().equals(ProductStatusEnum.DOWN.getCode())) {
//            productInfo = null;
//        }

        return category;
    }

    @DeleteMapping("/delete/category/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/category/status")
    public boolean changeStatus(@RequestParam Integer categoryId, @RequestParam Integer status) {
        return categoryService.changeStatus(categoryId, status);
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
