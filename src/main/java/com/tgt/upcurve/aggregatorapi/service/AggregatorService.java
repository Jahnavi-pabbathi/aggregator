package com.tgt.upcurve.aggregatorapi.service;

import com.tgt.upcurve.aggregatorapi.model.Order;
import com.tgt.upcurve.aggregatorapi.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AggregatorService {

    private OrderRepository orderRepository;
    public AggregatorService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrdersByCustomerId(Integer customerId) {
        return orderRepository.getOrdersByCustomerId(customerId);
    }
}
