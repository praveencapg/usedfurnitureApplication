package com.order.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.client.CartService;
import com.order.dto.CartDTO;
import com.order.dto.OrderDTO;
import com.order.entity.Order;
import com.order.exception.CartNotFoundException;
import com.order.exception.OrderNotFoundException;
import com.order.repository.OrderRepository;
import com.order.service.OrderService;
import com.order.util.Status;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    // Method to add orders for a customer
    public OrderDTO addOrders(int customerId) throws CartNotFoundException {
        // Retrieve the cart associated with the customer
        CartDTO cartDTO = cartService.getCartByCustomerId(customerId);

        // Throw exception if cart is not found
        if (cartDTO == null) {
            throw new CartNotFoundException("Cart Not Assigned to this customer. Please Order Again");
        }

        // Create a new order
        Order order = new Order();

        // Get the current date and format it
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);

        // Set order details
        order.setDate(formattedDate);
        order.setStatus(Status.PENDING);
        order.setCustomerId(customerId);
        order.setCartId(cartDTO.getId());
        order.setProductIds(cartDTO.getProductIds());
        order.setTotalPrice(cartDTO.getTotalPrice());
        order.setTotalQuantity(cartDTO.getTotalQuantity());

        // Save the order
        orderRepository.save(order);

        // Map the order entity to DTO and return
        return mapToOrderDTO(order);
    }

    // Method to get an order by its ID
    public OrderDTO getOrderById(int id) throws OrderNotFoundException {
        // Find the order by ID
        Order order = orderRepository.findById(id).orElse(null);

        // Throw exception if order not found
        if (order == null) {
            throw new OrderNotFoundException("Order Not Found By the given Id");
        }

        // Map the order entity to DTO and return
        return mapToOrderDTO(order);
    }

    // Method to map an order entity to DTO
    private OrderDTO mapToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setDate(order.getDate());
        orderDTO.setCustomerId(order.getCustomerId());
        orderDTO.setCartId(order.getCartId());
        orderDTO.setProductIds(order.getProductIds());
        orderDTO.setTotalQuantity(order.getTotalQuantity());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setStatus(order.getStatus());
        return orderDTO;
    }

    // Method to retrieve all orders
    public List<Order> getAllOrders() throws OrderNotFoundException {
        // Retrieve all orders
        List<Order> orders = orderRepository.findAll();

        // Throw exception if no orders found
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("There are no Orders.");
        } else {
            return orders;
        }
    }

    // Method to delete an order by its ID
    public String deleteOrders(int orderId) throws CartNotFoundException {
        // Retrieve the associated cart
        CartDTO cartDTO = cartService.getCartByCustomerId(orderId);

        // Throw exception if cart not found
        if (cartDTO == null) {
            throw new CartNotFoundException("Cart Not Found");
        }

        // Clear the product IDs from the cart DTO
        cartDTO.getProductIds().clear();

        // Delete the order by its ID
        orderRepository.deleteById(orderId);

        return "Deleted Successfully";
    }

    // Method to get an order by customer ID
    public Order getOrderByCustomerId(int customerId) throws CartNotFoundException, OrderNotFoundException {
        // Retrieve the cart associated with the customer
        CartDTO cartDTO = cartService.getCartByCustomerId(customerId);

        // Throw exception if cart not found
        if (cartDTO == null) {
            throw new CartNotFoundException("Cart Not Found By the given Customer Id");
        }

        // Retrieve the order using the cart ID
        Order order = orderRepository.getOrderByCartId(cartDTO.getId());

        // Throw exception if order not found
        if (order == null) {
            throw new OrderNotFoundException("Cart Not Found By the given Customer Id");
        }

        return order;
    }

}
