package com.shopzone.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 160)
    private String name;
    @Column(nullable = false, length = 40)
    private String category;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal oldPrice;
    private double rating;
    private int reviewCount;
    private int discount;
    private String emoji;
    @Column(length = 1200)
    private String description;
    @Column(length = 1200)
    private String features;

    public Product() {}
    public Product(String name, String category, BigDecimal price, BigDecimal oldPrice,
                   double rating, int reviewCount, int discount, String emoji,
                   String description, String features) {
        this.name=name; this.category=category; this.price=price; this.oldPrice=oldPrice;
        this.rating=rating; this.reviewCount=reviewCount; this.discount=discount;
        this.emoji=emoji; this.description=description; this.features=features;
    }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getCategory(){return category;} public void setCategory(String v){category=v;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
    public BigDecimal getOldPrice(){return oldPrice;} public void setOldPrice(BigDecimal v){oldPrice=v;}
    public double getRating(){return rating;} public void setRating(double v){rating=v;}
    public int getReviewCount(){return reviewCount;} public void setReviewCount(int v){reviewCount=v;}
    public int getDiscount(){return discount;} public void setDiscount(int v){discount=v;}
    public String getEmoji(){return emoji;} public void setEmoji(String v){emoji=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public String getFeatures(){return features;} public void setFeatures(String v){features=v;}
}
