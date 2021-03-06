package me.sunrise.Controller;

import me.sunrise.dto.ProductDTO;
import me.sunrise.dto.request.ProductRequest;
import me.sunrise.dto.request.SearchRequest;
import me.sunrise.entity.ProductEnity;
import me.sunrise.service.CategoryService;
import me.sunrise.service.ProductService;
import me.sunrise.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin("*")
@RestController
public class ProductController extends BaseService {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    /**
     * Show All Categories
     */

//    @GetMapping("/product")
//    public Page<ProductEnity> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
//                                      @RequestParam(value = "size", defaultValue = "3") Integer size) {
//        PageRequest request = PageRequest.of(page - 1, size);
//        return productService.findAll(request);
//    }
    @PostMapping("/products/page")
    public ResponseEntity<?> pageProducts(@RequestBody SearchRequest keySearch,
                                          @RequestParam(value = "page") Integer page,
                                          @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Page<ProductEnity> productPage = productService.findAll(keySearch.getKeySearch(), pageable);
        if (productPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(productPage, HttpStatus.OK);
        }
    }

    @GetMapping("products/page")
    public ResponseEntity<?> pageProductByCategory(@RequestParam Integer categoryType,
                                                   @RequestParam(value = "page") Integer page,
                                                   @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Page<ProductEnity> productPage = productService.findAllPageCategory(categoryType, pageable);
        if (productPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(productPage, HttpStatus.OK);
        }
    }

    @GetMapping("/products")
    public List<ProductEnity> findAll() {
        return productService.findAll();
    }

    @GetMapping("/product/{productId}")
    public ProductEnity showOne(@PathVariable("productId") String productId) {

        ProductEnity productInfo = productService.findOne(productId);

//        // Product is not available
//        if (productInfo.getProductStatus().equals(ProductStatusEnum.DOWN.getCode())) {
//            productInfo = null;
//        }

        return productInfo;
    }

//    @PostMapping("/seller/product/new")
//    public ResponseEntity<?> add(@RequestBody ProductEnity product) {
//        try {
//            ProductEnity returnedProduct = productService.save(product);
//            System.out.println("dsfs"+ returnedProduct);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/seller/product/new")
    public ResponseEntity<?> add(@RequestBody ProductEnity product) {
        System.out.println(product.toString());
        try {
            String uniqueID = UUID.randomUUID().toString();
            product.setProductId(uniqueID);
            ProductEnity returnedProduct = productService.save(product);
            System.out.println("dsfs" + returnedProduct);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/seller/product/{id}/edit")
    public ResponseEntity edit(@PathVariable("id") String productId,
                               @Valid @RequestBody ProductEnity product,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        if (!productId.equals(product.getProductId())) {
            return ResponseEntity.badRequest().body("Id Not Matched");
        }
        return ResponseEntity.ok(productService.update(product));
    }

    @DeleteMapping("/delete/product/{id}")
    public ResponseEntity delete(@PathVariable("id") String productId) {
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/product/under25")
    public Page<ProductEnity> under25(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return productService.under25(request);
    }

    @GetMapping("/product/from25to50")
    public Page<ProductEnity> from25to50(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return productService.from25to50(request);
    }

    @GetMapping("/product/from50to100")
    public Page<ProductEnity> from50to100(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return productService.from50to100(request);
    }

    @GetMapping("/product/from100to200")
    public Page<ProductEnity> from100to200(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return productService.from100to200(request);
    }

    @GetMapping("/product/above200")
    public Page<ProductEnity> above200(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return productService.above200(request);
    }

    @PostMapping("/product/status")
    public boolean changeStatus(@RequestParam String productId, @RequestParam Integer status) {
        return productService.changeStatus(productId, status);
    }

    @PostMapping("/product/dosearch")
    public List<ProductDTO> dosearch(@RequestBody ProductDTO keySearch) {
        return productService.doSearchProduct(keySearch);
    }

    @RequestMapping(value = "/products/newUpload", method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(@RequestBody ProductEnity product) {
        System.out.println("requets " + product.toString());
        try {
            // upload image tra ve ten anh de luu product
            String uniqueID = UUID.randomUUID().toString();
            product.setProductId(uniqueID);
            ProductEnity returnedProduct = productService.save(product);
            System.out.println("dsfs" + returnedProduct);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/products/uploadImage", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public Map<String, Object> uploadImageProduct(@RequestParam("file") MultipartFile image) {
        Map<String, Object> map = new HashMap<>();
        String imageName = productService.uploadProduct(image);
        map.put("imageName", imageName);
        return map;
    }

    @RequestMapping(value = "/products/editUpload", method = RequestMethod.POST)
    public ResponseEntity<?> editProduct(@RequestBody ProductRequest product) {
        try {
            if (product.getProductId() != null) {
                // upload image tra ve ten anh de luu product
                ProductEnity returnedProduct = productService.save(super.map(product, ProductEnity.class));
                System.out.println("update product" + returnedProduct);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
