package com.shopzone.controller;

import com.shopzone.dto.AuthRequest;
import com.shopzone.model.Customer;
import com.shopzone.repository.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final CustomerRepository customers;
    private final PasswordEncoder encoder;
    public AuthController(CustomerRepository customers,PasswordEncoder encoder){this.customers=customers;this.encoder=encoder;}

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String,Object> register(@Valid @RequestBody AuthRequest request,HttpSession session){
        if(request.getName()==null || request.getName().trim().length()<2)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Name must contain at least 2 characters");
        if(customers.existsByEmailIgnoreCase(request.getEmail()))
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email is already registered");
        Customer c=customers.save(new Customer(request.getName().trim(),request.getEmail().trim().toLowerCase(),encoder.encode(request.getPassword())));
        session.setAttribute("customerId",c.getId());
        return Map.of("id",c.getId(),"name",c.getName(),"email",c.getEmail());
    }

    @PostMapping("/login")
    public Map<String,Object> login(@Valid @RequestBody AuthRequest request,HttpSession session){
        Customer c=customers.findByEmailIgnoreCase(request.getEmail().trim())
            .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password"));
        if(!encoder.matches(request.getPassword(),c.getPasswordHash()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password");
        session.setAttribute("customerId",c.getId());
        return Map.of("id",c.getId(),"name",c.getName(),"email",c.getEmail());
    }

    @GetMapping("/me")
    public Map<String,Object> me(HttpSession session){
        Long id=(Long)session.getAttribute("customerId");
        if(id==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Please sign in");
        Customer c=customers.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return Map.of("id",c.getId(),"name",c.getName(),"email",c.getEmail());
    }

    @PostMapping("/logout")
    public Map<String,String> logout(HttpSession session){session.invalidate();return Map.of("message","Signed out");}
}
