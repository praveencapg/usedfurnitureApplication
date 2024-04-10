package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.dto.PaymentDTO;
import com.payment.entity.Payment;
import com.payment.exception.OrderNotFoundException;
import com.payment.exception.PaymentException;
import com.payment.serviceimpl.PaymentServiceImpl;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*")  //front-end connection
public class PaymentController {
    
    @Autowired
    private  PaymentServiceImpl paymentServiceImpl;

    //  to make a payment
    @PostMapping("/makepayment")
    public PaymentDTO makePayment(@RequestBody Payment payment) throws OrderNotFoundException {
        // Calls the service layer to make a payment and returns the DTO representation of the payment
        return paymentServiceImpl.makePayment(payment);
    }
    
    //  to retrieve a payment by ID
    @GetMapping("/getbyid/{payment_Id}")
    public PaymentDTO getPaymentById(@PathVariable(value="payment_Id") int paymentId) throws PaymentException {
        // Calls the service layer to retrieve a payment by ID
        return paymentServiceImpl.getPaymentById(paymentId);
    }
}
