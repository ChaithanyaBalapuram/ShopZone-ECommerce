package com.shopzone.config;

import com.shopzone.model.Product;
import com.shopzone.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedProducts(ProductRepository repository) {
        return args -> {
            if (repository.count() > 0) return;
            repository.saveAll(List.of(
                p("Studio Wireless Headphones","electronics",2499,3999,4.6,1248,38,"🎧","Comfortable wireless headphones for music, podcasts and calls.","Comfort fit|Bluetooth|Built-in microphone|Long battery"),
                p("Smartwatch with Fitness Tracking","electronics",1899,2999,4.3,862,37,"⌚","Track everyday activity and receive useful notifications.","Activity tracking|Notifications|Multiple watch faces|Adjustable strap"),
                p("Everyday Running Sneakers","fashion",1499,2499,4.4,530,40,"👟","Lightweight sneakers for walks and casual outings.","Lightweight|Cushioned footbed|Durable outsole"),
                p("Minimal Ceramic Table Lamp","home",999,1499,4.2,316,33,"💡","A simple accent for a bedside table or reading corner.","Ceramic base|Ambient lighting|Compact"),
                p("Portable Bluetooth Speaker","electronics",1299,1999,4.5,978,35,"🔊","Portable speaker for music at home or on the go.","Bluetooth|Portable|Rechargeable"),
                p("Classic Everyday Backpack","fashion",899,1399,4.1,421,36,"🎒","Versatile backpack for commutes, classes and short trips.","Main compartment|Utility pocket|Adjustable straps"),
                p("The Art of Thinking Clearly","books",349,599,4.7,2104,42,"📘","An accessible introduction to common thinking errors.","Paperback|Accessible chapters|Decision making"),
                p("Stainless Steel Cookware Set","home",2199,3499,4.3,287,37,"🍳","Practical cookware for everyday home cooking.","Stainless steel|Easy care|Multiple pieces"),
                p("Wireless Optical Mouse","electronics",599,999,4.4,1570,40,"🖱️","Compact wireless mouse for work and study.","Wireless|Optical tracking|Compact"),
                p("Cotton Everyday T-Shirt","fashion",499,799,4.0,694,38,"👕","An easy-to-style everyday wardrobe staple.","Cotton-rich|Regular fit|Everyday comfort"),
                p("Indoor Ceramic Planter","home",399,699,4.2,198,43,"🪴","A clean, simple home for an indoor plant.","Ceramic|Modern look|Indoor use"),
                p("Atomic Habits","books",399,699,4.8,3890,43,"📗","A practical guide to building better habits.","Paperback|Practical examples|Personal development")
            ));
        };
    }
    private Product p(String n,String c,int price,int old,double rating,int reviews,int discount,String emoji,String desc,String features){
        return new Product(n,c,BigDecimal.valueOf(price),BigDecimal.valueOf(old),rating,reviews,discount,emoji,desc,features);
    }
}
