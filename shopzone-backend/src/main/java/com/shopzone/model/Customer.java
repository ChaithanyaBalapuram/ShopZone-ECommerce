package com.shopzone.model;

import jakarta.persistence.*;

@Entity
@Table(name="customers", uniqueConstraints=@UniqueConstraint(columnNames="email"))
public class Customer {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length=100) private String name;
    @Column(nullable=false, unique=true, length=160) private String email;
    @Column(nullable=false, length=100) private String passwordHash;
    public Customer() {}
    public Customer(String name,String email,String passwordHash){this.name=name;this.email=email;this.passwordHash=passwordHash;}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
}
