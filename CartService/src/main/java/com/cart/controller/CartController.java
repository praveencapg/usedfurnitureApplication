package com.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.dto.CartDTO;
import com.cart.exception.CartNotFoundException;
import com.cart.exception.CustomerNotFoundException;
import com.cart.exception.ProductNotFoundException;
import com.cart.serviceimpl.CartServiceImpl;

@RestController
@RequestMapping("/cart") 
@CrossOrigin(origins = "*") // Front-end Connection
public class CartController {

    @Autowired
    CartServiceImpl cartServiceImpl;
    

    //  to add a product to the cart
    @PostMapping("/add/{customerId}/{productId}")
    public CartDTO addToCart(@PathVariable(value = "customerId") int customerId,
                             @PathVariable(value = "productId") int productId) throws CustomerNotFoundException, ProductNotFoundException {
        // Calls the service layer to add a product to the cart for the given customer
        return cartServiceImpl.addToCart(customerId, productId);
    }

    //  to delete a product from the cart
    @DeleteMapping("/deleteProducts/{customerId}/{productId}")
    public String deleteProductCart(@PathVariable(value = "customerId") int customerId,
                                    @PathVariable(value = "productId") int productId) throws CustomerNotFoundException, ProductNotFoundException, CartNotFoundException {
        // Calls the service layer to delete a product from the cart for the given customer
        return cartServiceImpl.deleteProduct(customerId, productId);
    }

    //  to retrieve the cart for a given customer
    @GetMapping("/getcartbycustid/{customerId}") 
    public CartDTO getCartByCustomerId(@PathVariable(value = "customerId") int customerId) throws CartNotFoundException {
        // Calls the service layer to find the cart for the given customer
        return cartServiceImpl.findCartByCustomerId(customerId);
    }
}
