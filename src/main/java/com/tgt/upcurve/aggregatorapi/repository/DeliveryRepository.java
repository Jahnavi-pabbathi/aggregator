package com.tgt.upcurve.aggregatorapi.repository;

import com.tgt.upcurve.aggregatorapi.model.Delivery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="delivery-api", url="${apis.delivery}")
public interface DeliveryRepository {
    @RequestMapping(method = RequestMethod.POST)
    public Delivery saveDelivery(@Validated @RequestBody Delivery delivery);

    @RequestMapping(method = RequestMethod.GET, path = "/fetch_delivery_by_id/customer_id/{customer_id}/order_id/{order_id}")
    public Delivery getDeliveryInfoByCustomerIdAndOrderId(@Validated @PathVariable("customer_id") Integer customerId,
                                                          @Validated @PathVariable("order_id") Integer orderId);
}
