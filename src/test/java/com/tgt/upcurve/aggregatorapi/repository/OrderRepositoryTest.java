package com.tgt.upcurve.aggregatorapi.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tgt.upcurve.aggregatorapi.model.Order;
import com.tgt.upcurve.aggregatorapi.utility.JsonUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderRepositoryTest {

    @Autowired
    MockMvc mockMvc;

    private static final String URI_FETCH_CUSTOMER_ID = "/order_api/v1/fetch_order_by_customer_id/{customer_id}";
    private static final String ORDER_JSON_FILE_PATH = "/orderData.json";
    private static final String URI_SAVE = "/order_api/v1/";
    private static final String URI_FETCH_CUSTOMER_ID_ORDER_ID = "/order_api/v1/fetch_order_by_id/customer_id/{customer_id}/order_id/{order_id}";

    @Test
    public void testGetOrdersByCustomerId() throws Exception {
        String orderString = JsonUtility.getResourceAsString(ORDER_JSON_FILE_PATH);
        MvcResult responseSave = mockMvc.perform(post(URI_SAVE)
                        .content(orderString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String savedResponse = responseSave.getResponse().getContentAsString();
        MvcResult responseFetch = mockMvc.perform(get(URI_FETCH_CUSTOMER_ID, 20)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String fetchedResponse = responseFetch.getResponse().getContentAsString();
        Order savedOrder = JsonUtility.readValue(savedResponse, Order.class);
        List<Order> fetchedOrder =JsonUtility.readValue(fetchedResponse, new TypeReference<List<Order>>() {
        });

        Assertions.assertEquals(savedOrder.getOrderId(), fetchedOrder.get(0).getOrderId());
        Assertions.assertEquals(savedOrder.getCustomerId(), fetchedOrder.get(0).getCustomerId());
        Assertions.assertEquals(savedOrder.getStoreId(), fetchedOrder.get(0).getStoreId());
        Assertions.assertEquals(savedOrder.getOrderAmount(), fetchedOrder.get(0).getOrderAmount());
        Assertions.assertEquals(savedOrder.getOrderItems().size(), fetchedOrder.get(0).getOrderItems().size());
    }

    @Test
    public void testGetOrderByCustomerIdAndOrderId() throws Exception {
        String orderString = JsonUtility.getResourceAsString(ORDER_JSON_FILE_PATH);
        MvcResult responseSave = mockMvc.perform(post(URI_SAVE)
                        .content(orderString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String savedResponse = responseSave.getResponse().getContentAsString();
        MvcResult responseFetch = mockMvc.perform(get(URI_FETCH_CUSTOMER_ID_ORDER_ID, 20, 200)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String fetchedResponse = responseFetch.getResponse().getContentAsString();


        Order savedOrder = JsonUtility.readValue(savedResponse, Order.class);
        Order fetchedOrder = JsonUtility.readValue(fetchedResponse, Order.class);
        Assertions.assertEquals(savedOrder.getOrderId(), fetchedOrder.getOrderId());
        Assertions.assertEquals(savedOrder.getCustomerId(), fetchedOrder.getCustomerId());
        Assertions.assertEquals(savedOrder.getStoreId(), fetchedOrder.getStoreId());
        Assertions.assertEquals(savedOrder.getOrderAmount(), fetchedOrder.getOrderAmount());
        Assertions.assertEquals(savedOrder.getOrderItems().size(), fetchedOrder.getOrderItems().size());
    }
}

