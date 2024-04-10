package com.cart.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.client.CustomerService;
import com.cart.client.ProductService;
import com.cart.dto.CartDTO;
import com.cart.dto.CustomerDTO;
import com.cart.dto.ProductDTO;
import com.cart.entity.Cart;
import com.cart.exception.CartNotFoundException;
import com.cart.exception.CustomerNotFoundException;
import com.cart.exception.ProductNotFoundException;
import com.cart.repository.CartRepository;
import com.cart.service.CartService;

@Service
public class CartServiceImpl implements CartService {
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepository cartRepository;

    // Method to add a product to the cart
    public CartDTO addToCart(int customerId, int productId) throws CustomerNotFoundException, ProductNotFoundException {
        // Retrieve customer details by ID
        CustomerDTO customer = customerService.getCustomerById(customerId);

        // Throw exception if customer not found
        if (customer == null) {
            throw new CustomerNotFoundException("Customer Not Found by the Id");
        }

        // Retrieve product details by ID
        ProductDTO product = productService.getProductById(productId);

        // Throw exception if product not found
        if (product == null) {
            throw new ProductNotFoundException("Product Not Found by the Id");
        }

        // Find the cart by customer ID
        Cart cart = cartRepository.findByCustomerId(customerId);

        // Create a new cart if not found
        if (cart == null) {
            cart = new Cart();
            cart.setCustomerId(customerId);
            cart.setProductIds(new ArrayList<>());
        }

        // Add the product to the cart
        List<Integer> products = cart.getProductIds();
        products.add(productId);
        cart.setProductIds(products);
        cart.setTotalPrice(product.getProductPrice() + cart.getTotalPrice());
        cart.setTotalQuantity(cart.getTotalQuantity() + 1);
        cartRepository.save(cart);

        // Map cart entity to DTO and return
        return mapToCartDTO(cart);
    }

    // Method to delete a product from the cart
    public String deleteProduct(int custid, int prodid) throws CustomerNotFoundException, ProductNotFoundException, CartNotFoundException {
        // Retrieve customer details by ID
        CustomerDTO customer = customerService.getCustomerById(custid);

        // Throw exception if customer not found
        if (customer == null) {
            throw new CustomerNotFoundException("Customer Not Found by the Id");
        }

        // Retrieve cart by customer ID
        Cart cart = cartRepository.findByCustomerId(custid);

        // Throw exception if cart not found
        if (cart == null) {
            throw new CartNotFoundException("Cart Not Found by the Id");
        }

        // Retrieve products from the cart
        List<Integer> products = cart.getProductIds();

        // Check if cart is empty
        if (!products.isEmpty()) {
            // Remove the specified product from the cart
            products.remove(prodid);
            return "Product Deleted Successfully";
        } else {
            // Throw exception if cart is empty
            throw new ProductNotFoundException("Cart is Empty");
        }
    }

    // Method to find cart by customer ID
    public CartDTO findCartByCustomerId(int customerId) throws CartNotFoundException {
        // Find the cart by customer ID
        Cart cart = cartRepository.findByCustomerId(customerId);

        // Throw exception if cart not found
        if (cart == null) {
            throw new CartNotFoundException("Cart Not Found Exception");
        }
        // Map cart entity to DTO and return
        return mapToCartDTO(cart);
    }

    // Method to map cart entity to DTO
    public CartDTO mapToCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setCustomerId(cart.getCustomerId());
        cartDTO.setProductIds(cart.getProductIds());
        cartDTO.setTotalPrice(cart.getTotalPrice());
        cartDTO.setTotalQuantity(cart.getTotalQuantity());
        return cartDTO;
    }
}
