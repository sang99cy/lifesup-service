package me.sunrise.Controller;


import me.sunrise.entity.OrderMainEntity;
import me.sunrise.entity.ProductInOrderEntity;
import me.sunrise.enums.OrderStatusEnum;
import me.sunrise.repository.OrderRepository;
import me.sunrise.service.OrderService;
import me.sunrise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/orders")
    public List<OrderMainEntity> orderList() {
        return  orderRepository.findAll();
    }

    @GetMapping("/order")
    public Page<OrderMainEntity> orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                                           Authentication authentication) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderMainEntity> orderPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            orderPage = orderService.findByBuyerEmail(authentication.getName(), request);
        } else {
            orderPage = orderService.findAll(request);
        }
        orderPage = orderService.findAll(request);
        return orderPage;
    }

    @GetMapping("/order/chart")
    public Page<OrderMainEntity> orderListchart(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                Authentication authentication) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderMainEntity> orderPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            orderPage = orderService.findByStatus(OrderStatusEnum.FINISHED.getCode(), Pageable.unpaged());
        } else {
            orderPage = orderService.findAll(request);
        }
        return orderPage;
    }

    @PatchMapping("/order/cancel/{id}")
    public ResponseEntity<OrderMainEntity> cancel(@PathVariable("id") Long orderId, Authentication authentication) {
        OrderMainEntity orderMain = orderService.findOne(orderId);
        if (!authentication.getName().equals(orderMain.getBuyerEmail()) && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderService.cancel(orderId));
    }

    @PatchMapping("/order/approved/{id}")
    public ResponseEntity<OrderMainEntity> approved(@PathVariable("id") Long orderId, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderService.approved(orderId));
    }

    @PatchMapping("/order/finish/{id}")
    public ResponseEntity<OrderMainEntity> finish(@PathVariable("id") Long orderId, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderService.finish(orderId));
    }

    @GetMapping("/order/{id}")
    public ResponseEntity show(@PathVariable("id") Long orderId, Authentication authentication) {
        boolean isCustomer = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        OrderMainEntity orderMain = orderService.findOne(orderId);
        if (isCustomer && !authentication.getName().equals(orderMain.getBuyerEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        //Collection<ProductInOrderEntity> items = orderMain.getProducts();
        return ResponseEntity.ok(orderMain);
    }

    @GetMapping("/order/status1")
    public Page<OrderMainEntity> getStatus1(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                                            Authentication authentication) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderMainEntity> orderPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            orderPage = orderService.findByBuyerEmail(authentication.getName(), request);
        } else {
            orderPage = orderService.findstatus1(request);
        }
        return orderPage;
    }


    @GetMapping("/order/status2")
    public Page<OrderMainEntity> getStatus2(@RequestParam(value = "page2", defaultValue = "1") Integer page2,
                                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                                            Authentication authentication) {
        PageRequest request = PageRequest.of(page2 - 1, size);
        Page<OrderMainEntity> orderPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            orderPage = orderService.findByBuyerEmail(authentication.getName(), request);
        } else {
            orderPage = orderService.findstatus2(request);
        }
        return orderPage;
    }
    @GetMapping("/order/status3")
    public Page<OrderMainEntity> getStatus3(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                                            Authentication authentication) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderMainEntity> orderPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            orderPage = orderService.findByBuyerEmail(authentication.getName(), request);
        } else {
            orderPage = orderService.findstatus3(request);
        }
        return orderPage;
    }
    @GetMapping("/order/status0")
    public Page<OrderMainEntity> getStatus0(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                                            Authentication authentication) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderMainEntity> orderPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            orderPage = orderService.findByBuyerEmail(authentication.getName(), request);
        } else {
            orderPage = orderService.findstatus0(request);
        }
        return orderPage;
    }

    @GetMapping("/order/getCountStatus0")
    public Object[] getCountStatus0( Authentication authentication) {

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

        }return orderService.getcountStautus0();

    }
    @GetMapping("/order/getCountStatus1")
    public Object[] getCountStatus1( Authentication authentication) {

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

        }return orderService.getcountStautus1();

    }
    @GetMapping("/order/getCountStatus2")
    public Object[] getCountStatus2( Authentication authentication) {

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

        }return orderService.getcountStautus2();

    }
    @GetMapping("/order/getCountStatus3")
    public Object[] getCountStatus3( Authentication authentication) {

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

        }return orderService.getcountStautus3();

    }
    @GetMapping("/order/getCountAll")
    public Object[] getCountAll( Authentication authentication) {

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

        }return orderService.getcountAll();

    }

    @GetMapping("/order/getSumStatus0")
    public Object[] getSumStatus0( Authentication authentication) {

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

        }return orderService.getsumStautus0();

    }
    @GetMapping("/order/getSumStatus1")
    public Object[] getSumStatus1( Authentication authentication) {

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

        }return orderService.getsumStautus1();

    }
    @GetMapping("/order/getSumStatus2")
    public Object[] getSumStatus2( Authentication authentication) {

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

        }return orderService.getsumStautus2();

    }
    @GetMapping("/order/getSumStatus3")
    public Object[] getSumStatus3( Authentication authentication) {

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

        }return orderService.getsumStautus3();

    }

    @GetMapping("/order/getSumAll")
    public Object[] getSumAll( Authentication authentication) {

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {

        }return orderService.getsumAll();

    }

}
