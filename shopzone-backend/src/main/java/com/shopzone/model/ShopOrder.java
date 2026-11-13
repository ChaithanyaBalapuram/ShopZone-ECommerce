package com.shopzone.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="shop_orders")
public class ShopOrder {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true) private String orderNumber;
    @Column(nullable=false) private Long customerId;
    @Column(nullable=false) private LocalDateTime createdAt;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal total;
    @Column(nullable=false) private String status;
    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
    private List<ShopOrderItem> items = new ArrayList<>();
    public ShopOrder() {}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getOrderNumber(){return orderNumber;} public void setOrderNumber(String v){orderNumber=v;}
    public Long getCustomerId(){return customerId;} public void setCustomerId(Long v){customerId=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
    public BigDecimal getTotal(){return total;} public void setTotal(BigDecimal v){total=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public List<ShopOrderItem> getItems(){return items;} public void setItems(List<ShopOrderItem> v){items=v;}
}
