package me.sunrise.Controller;


import me.sunrise.entity.Cart;
import me.sunrise.entity.ProductInOrderEntity;
import me.sunrise.entity.ProductEnity;
import me.sunrise.entity.UserEntity;
import me.sunrise.form.ItemForm;
import me.sunrise.repository.ProductInOrderRepository;
import me.sunrise.service.CartService;
import me.sunrise.service.ProductInOrderService;
import me.sunrise.service.ProductService;
import me.sunrise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductInOrderService productInOrderService;
    @Autowired
    ProductInOrderRepository productInOrderRepository;

    @PostMapping("")
    public ResponseEntity<Cart> mergeCart(@RequestBody Collection<ProductInOrderEntity> productInOrders, Principal principal) {
        UserEntity UserEntity = userService.findOne(principal.getName());
        try {
            cartService.mergeLocalCart(productInOrders, UserEntity);
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Merge Cart Failed");
        }
        return ResponseEntity.ok(cartService.getCart(UserEntity));
    }

    @GetMapping("")
    public Cart getCart(Principal principal) {
        UserEntity UserEntity = userService.findOne(principal.getName());
        return cartService.getCart(UserEntity);
    }


    @PostMapping("/add")
    public boolean addToCart(@RequestBody ItemForm form, Principal principal) {
        ProductEnity productInfo = productService.findOne(form.getProductId());
        try {
            mergeCart(Collections.singleton(new ProductInOrderEntity(productInfo, form.getQuantity())), principal);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PutMapping("/{itemId}")
    public ProductInOrderEntity modifyItem(@PathVariable("itemId") String itemId, @RequestBody Integer quantity, Principal principal) {
        UserEntity UserEntity = userService.findOne(principal.getName());
        productInOrderService.update(itemId, quantity, UserEntity);
        return productInOrderService.findOne(itemId, UserEntity);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") String itemId, Principal principal) {
        UserEntity UserEntity = userService.findOne(principal.getName());
        cartService.delete(itemId, UserEntity);
        // flush memory into DB
    }


    @PostMapping("/checkout")
    public ResponseEntity checkout(Principal principal) {
        UserEntity UserEntity = userService.findOne(principal.getName());// Email as username
        cartService.checkout(UserEntity);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/productInOrder")
    public void checkout(@RequestBody List<ProductInOrderEntity> productInOrders) {
        productInOrderService.saveProductInOrder(productInOrders);
    }

}
