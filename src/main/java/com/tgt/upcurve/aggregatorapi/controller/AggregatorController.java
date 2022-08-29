package com.tgt.upcurve.aggregatorapi.controller;

import com.tgt.upcurve.aggregatorapi.model.Delivery;
import com.tgt.upcurve.aggregatorapi.model.Order;
import com.tgt.upcurve.aggregatorapi.service.AggregatorService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aggregator_api/v1")
public class AggregatorController {
    private AggregatorService aggregatorService;

    public AggregatorController(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @GetMapping("/get_orders/customer_id/{customer_id}")
    public List<Order> getOrders(@Validated @PathVariable("customer_id") Integer customerId){
        return aggregatorService.getOrdersByCustomerId(customerId);
    }

    @GetMapping("/generate_qr/order_id/{order_id}/customer_id/{customer_id}")
    public Delivery generateQRCode(@Validated @PathVariable("order_id")
                                               Integer orderId, @PathVariable("customer_id")
            Integer customerId){
        return aggregatorService.generateQRCode(orderId, customerId);
    }

    @PostMapping("/confirm_delivery/order_id/{order_id}/customer_id/{customer_id}")
    public Delivery confirmDelivery(@Validated @PathVariable("order_id") Integer orderId,
                                    @PathVariable("customer_id") Integer customerId){
        return aggregatorService.confirmDelivery(orderId, customerId);
    }

    @PostMapping("/guest_arrival/order_id/{order_id}/customer_id/{customer_id}")
    public Boolean informCustomerArrival(@Validated @PathVariable("order_id") Integer orderId, @PathVariable("customer_id") Integer customerId){
        return aggregatorService.informCustomerArrival(orderId, customerId);
    }
}
