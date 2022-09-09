package com.tgt.upcurve.aggregatorapi.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;



import com.tgt.upcurve.aggregatorapi.model.Delivery;

import com.tgt.upcurve.aggregatorapi.service.AggregatorService;

import java.time.LocalDateTime;

import java.util.ArrayList;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AggregatorController.class})
@ExtendWith(SpringExtension.class)
public class AggregatorControllerTest {

    @Autowired
    private AggregatorController aggregatorController;

    @MockBean
    private AggregatorService aggregatorService;

    @Test
    void testGenerateQRCode() throws Exception {
        Delivery delivery = new Delivery();
        delivery.setCustomerId(123);
        delivery.setDeliveryStatus("Delivery Status");
        delivery.setId(1);
        delivery.setImageCode("AAAAAAAA".getBytes("UTF-8"));
        delivery.setImageId(123L);
        delivery.setOrderId(123);
        delivery.setPaymentStatus("Payment Status");
        delivery.setPickupDate(LocalDateTime.of(1, 1, 1, 1, 1));
        delivery.setStoreId(123);
        when(aggregatorService.generateQRCode((Integer) any(), (Integer) any())).thenReturn(delivery);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/aggregator_api/v1/generate_qr/order_id/{order_id}/customer_id/{customer_id}", 1, 1);
        MockMvcBuilders.standaloneSetup(aggregatorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"customer_id\":123,\"store_id\":123,\"order_id\":123,\"image_id\":123,\"image_code\":\"QUFBQUFBQUE=\","
                                        + "\"payment_status\":\"Payment Status\",\"delivery_status\":\"Delivery Status\",\"pickup_date\":[1,1,1,1,1]}"));
    }

    @Test
    void testConfirmDelivery() throws Exception {
        Delivery delivery = new Delivery();
        delivery.setCustomerId(123);
        delivery.setDeliveryStatus("Delivery Status");
        delivery.setId(1);
        delivery.setImageCode("AAAAAAAA".getBytes("UTF-8"));
        delivery.setImageId(123L);
        delivery.setOrderId(123);
        delivery.setPaymentStatus("Payment Status");
        delivery.setPickupDate(LocalDateTime.of(1, 1, 1, 1, 1));
        delivery.setStoreId(123);
        when(aggregatorService.confirmDelivery((Integer) any(), (Integer) any())).thenReturn(delivery);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/aggregator_api/v1/confirm_delivery/order_id/{order_id}/customer_id/{customer_id}", 1, 1);
        MockMvcBuilders.standaloneSetup(aggregatorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"customer_id\":123,\"store_id\":123,\"order_id\":123,\"image_id\":123,\"image_code\":\"QUFBQUFBQUE=\","
                                        + "\"payment_status\":\"Payment Status\",\"delivery_status\":\"Delivery Status\",\"pickup_date\":[1,1,1,1,1]}"));
    }

    @Test
    void testInformCustomerArrival() throws Exception {
        when(aggregatorService.informCustomerArrival((Integer) any(), (Integer) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/aggregator_api/v1/guest_arrival/order_id/{order_id}/customer_id/{customer_id}", 1, 1);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(aggregatorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testGetOrders() throws Exception {
        when(aggregatorService.getOrdersByCustomerId((Integer) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/aggregator_api/v1/get_orders/customer_id/{customer_id}", 1);
        MockMvcBuilders.standaloneSetup(aggregatorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
