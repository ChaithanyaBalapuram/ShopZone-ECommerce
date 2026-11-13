package com.shopzone.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public class AuthRequest {
    @Email @NotBlank private String email;
    @NotBlank @Size(min=6) private String password;
    @Size(min=2,max=100) private String name;
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPassword(){return password;} public void setPassword(String v){password=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
}
