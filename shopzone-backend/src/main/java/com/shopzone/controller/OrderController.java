package com.shopzone.controller;

import com.shopzone.model.*;
import com.shopzone.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final ShopOrderRepository orders;
    private final ProductRepository products;
    private final CustomerRepository customers;
    public OrderController(ShopOrderRepository orders,ProductRepository products,CustomerRepository customers){
        this.orders=orders;this.products=products;this.customers=customers;
    }
    @SuppressWarnings("unchecked")
    private Map<Long,Integer> cart(HttpSession s){
        Object v=s.getAttribute("cart");
        if(v==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Your cart is empty");
        return (Map<Long,Integer>)v;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShopOrder place(HttpSession session){
        Long customerId=(Long)session.getAttribute("customerId");
        if(customerId==null)throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Sign in before placing an order");
        Customer customer=customers.findById(customerId).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        Map<Long,Integer> cart=cart(session);
        if(cart.isEmpty())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Your cart is empty");
        ShopOrder order=new ShopOrder();
        order.setOrderNumber("SZ"+System.currentTimeMillis());
        order.setCustomerId(customer.getId());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("Order placed");
        BigDecimal total=BigDecimal.ZERO;
        for(var entry:cart.entrySet()){
            Product p=products.findById(entry.getKey()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"A cart product no longer exists"));
            BigDecimal line=p.getPrice().multiply(BigDecimal.valueOf(entry.getValue()));
            total=total.add(line);
            order.getItems().add(new ShopOrderItem(order,p.getId(),p.getName(),p.getPrice(),entry.getValue()));
        }
        order.setTotal(total);
        ShopOrder saved=orders.save(order);
        cart.clear();
        return saved;
    }
    @GetMapping
    public List<ShopOrder> mine(HttpSession session){
        Long id=(Long)session.getAttribute("customerId");
        if(id==null)throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Sign in to view orders");
        return orders.findByCustomerIdOrderByCreatedAtDesc(id);
    }
    @GetMapping("/{id}")
    public ShopOrder one(@PathVariable Long id,HttpSession session){
        Long customerId=(Long)session.getAttribute("customerId");
        if(customerId==null)throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        ShopOrder order=orders.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(!order.getCustomerId().equals(customerId))throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return order;
    }
}
