package com.payment.serviceimpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.client.OrderService;
import com.payment.dto.OrderDTO;
import com.payment.dto.PaymentDTO;
import com.payment.entity.Payment;
import com.payment.exception.OrderNotFoundException;
import com.payment.exception.PaymentException;
import com.payment.repository.PaymentRepository;
import com.payment.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderService orderService;
	
	@Override
	public PaymentDTO makePayment(Payment payment) throws OrderNotFoundException {
		// Create a PaymentDTO object to hold payment details
		PaymentDTO paymentDTO = new PaymentDTO();
		
		// Set payment amount and date
		paymentDTO.setAmount(payment.getAmount());
		paymentDTO.setPaymentDate(LocalDateTime.now());
		
		// Set initial payment status to "Pending"
		payment.setStatus("Pending");

		// Retrieve order details using provided order ID
		OrderDTO orderDTO = orderService.findById(payment.getOrderId());
		
		// Throw OrderNotFoundException if order is not found
		if(orderDTO == null) {
			throw new OrderNotFoundException("Order Not Found by the given Id");
		}
		
		// Set order ID for the payment
		payment.setOrderId(orderDTO.getOrderId());

		// Save the payment to the database
		Payment savedPayment = paymentRepository.save(payment);

		// Set payment details in the PaymentDTO object
		paymentDTO.setPaymentId(savedPayment.getPaymentId());
		paymentDTO.setPaymentDate(savedPayment.getPaymentDate());
		paymentDTO.setStatus(savedPayment.getStatus());

		// Return the PaymentDTO object
		return paymentDTO;
	}

	@Override
	public PaymentDTO getPaymentById(int paymentId) throws PaymentException {
		// Retrieve payment details from the database using the provided ID
		Payment payment = paymentRepository.findById(paymentId).orElse(null);
		
		// Throw PaymentException if payment is not found
		if(payment == null) {
			throw new PaymentException("Payment Not Found by the given Id");
		}
		
		// Create a PaymentDTO object and set payment details
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setPaymentId(payment.getPaymentId());
		paymentDTO.setAmount(payment.getAmount());
		paymentDTO.setPaymentDate(payment.getPaymentDate());
		paymentDTO.setStatus(payment.getStatus());
		
		// Return the PaymentDTO object
		return paymentDTO;
	}
}
