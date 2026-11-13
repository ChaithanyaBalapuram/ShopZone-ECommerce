package com.shopzone.controller;

import com.shopzone.model.Product;
import com.shopzone.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final ProductRepository products;
    public WishlistController(ProductRepository products){this.products=products;}
    @SuppressWarnings("unchecked")
    private Set<Long> list(HttpSession s){
        Object value=s.getAttribute("wishlist");
        if(value==null){Set<Long> fresh=new LinkedHashSet<>();s.setAttribute("wishlist",fresh);return fresh;}
        return (Set<Long>)value;
    }
    @GetMapping public List<Product> get(HttpSession s){return list(s).stream().map(id->products.findById(id).orElse(null)).filter(Objects::nonNull).toList();}
    @PostMapping("/{productId}") public List<Product> add(@PathVariable Long productId,HttpSession s){
        if(!products.existsById(productId))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found");
        list(s).add(productId);return get(s);
    }
    @DeleteMapping("/{productId}") public List<Product> remove(@PathVariable Long productId,HttpSession s){list(s).remove(productId);return get(s);}
}
