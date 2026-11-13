package com.shopzone.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
public class CartItemRequest {
    @NotNull private Long productId;
    @Min(1) private int quantity=1;
    public Long getProductId(){return productId;} public void setProductId(Long v){productId=v;}
    public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
}
