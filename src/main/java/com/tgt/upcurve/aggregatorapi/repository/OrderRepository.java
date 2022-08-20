package com.tgt.upcurve.aggregatorapi.repository;

import com.tgt.upcurve.aggregatorapi.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="order-api", url="${apis.order}")
public interface OrderRepository {
    @RequestMapping(method = RequestMethod.GET, path="/fetch_order_by_customer_id/{customer_id}")
    List<Order> getOrdersByCustomerId(@Validated @PathVariable("customer_id") Integer CustomerId);
}
