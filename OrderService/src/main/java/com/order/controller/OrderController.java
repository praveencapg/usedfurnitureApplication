package com.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.dto.OrderDTO;
import com.order.entity.Order;
import com.order.exception.CartNotFoundException;
import com.order.exception.OrderNotFoundException;
import com.order.serviceimpl.OrderServiceImpl;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*") // Front-end Connection
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    //  to place an order for a customer
    @PostMapping("/placeOrder/{custid}")
    public OrderDTO placeOrder(@PathVariable(value = "custid") int custid) throws CartNotFoundException {
        // Calls the service layer to add orders for the given customer ID
        return orderServiceImpl.addOrders(custid);
    }

    //  to retrieve an order by ID
    @GetMapping("/find/{orderId}")
    public OrderDTO findById(@PathVariable(value = "orderId") int orderId) throws OrderNotFoundException {
        // Calls the service layer to retrieve an order by ID
        return orderServiceImpl.getOrderById(orderId);
    }
    
    //  to retrieve all orders
    @GetMapping("/findall")
    public List<Order> FindAllOrders() throws OrderNotFoundException {
        // Calls the service layer to retrieve all orders
        return orderServiceImpl.getAllOrders();
    }

    //  to delete an order by ID
    @DeleteMapping("/deleteOrder/{order_id}")
    public String deleteOrder(@PathVariable(value = "order_id") int orderId) throws CartNotFoundException {
        // Calls the service layer to delete an order by ID
        return orderServiceImpl.deleteOrders(orderId);
    }

    //  to retrieve an order by customer ID
    @GetMapping("/findBycustId/{customer_Id}")
    public Order getOrderCustId(@PathVariable(value = "customer_Id") int custId) throws CartNotFoundException, OrderNotFoundException {
        // Calls the service layer to retrieve an order by customer ID
        return orderServiceImpl.getOrderByCustomerId(custId);
    }
}
