package com.shopzone.controller;

import com.shopzone.dto.CartItemRequest;
import com.shopzone.model.Product;
import com.shopzone.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final ProductRepository products;
    public CartController(ProductRepository products){this.products=products;}

    @SuppressWarnings("unchecked")
    private Map<Long,Integer> cart(HttpSession session){
        Object value=session.getAttribute("cart");
        if(value==null){Map<Long,Integer> fresh=new LinkedHashMap<>();session.setAttribute("cart",fresh);return fresh;}
        return (Map<Long,Integer>)value;
    }

    @GetMapping
    public Map<String,Object> get(HttpSession session){
        List<Map<String,Object>> items=new ArrayList<>(); BigDecimal total=BigDecimal.ZERO;
        for(var entry:cart(session).entrySet()){
            Product p=products.findById(entry.getKey()).orElse(null); if(p==null)continue;
            BigDecimal line=p.getPrice().multiply(BigDecimal.valueOf(entry.getValue())); total=total.add(line);
            items.add(Map.of("product",p,"quantity",entry.getValue(),"lineTotal",line));
        }
        return Map.of("items",items,"subtotal",total,"itemCount",items.stream().mapToInt(x->(int)x.get("quantity")).sum());
    }

    @PostMapping("/items")
    public Map<String,Object> add(@Valid @RequestBody CartItemRequest request,HttpSession session){
        if(!products.existsById(request.getProductId()))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found");
        cart(session).merge(request.getProductId(),request.getQuantity(),Integer::sum);
        return get(session);
    }

    @PutMapping("/items/{productId}")
    public Map<String,Object> update(@PathVariable Long productId,@RequestBody CartItemRequest request,HttpSession session){
        if(!products.existsById(productId))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found");
        if(request.getQuantity()<1)cart(session).remove(productId);else cart(session).put(productId,request.getQuantity());
        return get(session);
    }

    @DeleteMapping("/items/{productId}")
    public Map<String,Object> remove(@PathVariable Long productId,HttpSession session){cart(session).remove(productId);return get(session);}
    @DeleteMapping
    public Map<String,Object> clear(HttpSession session){cart(session).clear();return get(session);}
}
