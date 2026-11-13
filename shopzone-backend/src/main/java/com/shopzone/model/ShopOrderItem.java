package com.shopzone.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

@Entity
@Table(name="shop_order_items")
public class ShopOrderItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="order_id", nullable=false)
    private ShopOrder order;
    @Column(nullable=false) private Long productId;
    @Column(nullable=false) private String productName;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal unitPrice;
    @Column(nullable=false) private int quantity;
    public ShopOrderItem() {}
    public ShopOrderItem(ShopOrder order,Long productId,String productName,BigDecimal unitPrice,int quantity){
        this.order=order;this.productId=productId;this.productName=productName;this.unitPrice=unitPrice;this.quantity=quantity;
    }
    public Long getId(){return id;} public Long getProductId(){return productId;}
    public String getProductName(){return productName;} public BigDecimal getUnitPrice(){return unitPrice;}
    public int getQuantity(){return quantity;}
}
