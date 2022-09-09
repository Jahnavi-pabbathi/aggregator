package com.tgt.upcurve.aggregatorapi.repository;

import com.tgt.upcurve.aggregatorapi.model.Delivery;
import com.tgt.upcurve.aggregatorapi.utility.JsonUtility;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DeliveryRepositoryTest {

    //@MockBean
    //MockMvc mockMvc;
    private MockMvc mockMvc;
    private MockRestServiceServer mockRestServiceServer;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private RestOperations restOperations;

    private static final String URI_FETCH_CUSTOMER_ID_ORDER_ID = "/fetch_delivery_by_id/customer_id/{customer_id}/order_id/{order_id}";
    private static final String DELIVERY_JSON_FILE_PATH = "/deliveryData.json";
    private static final String URI_SAVE = "/delivery_api/v1/";

    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        mockRestServiceServer = MockRestServiceServer.createServer((RestTemplate) restOperations);
    }
    @Test
    public void testGetDeliveryInfoByCustomerIdAndOrderId() throws Exception {
        setUp();
        String deliveryString = JsonUtility.getResourceAsString(DELIVERY_JSON_FILE_PATH);
        mockRestServiceServer.expect(requestTo(URI_SAVE))
                .andRespond(withSuccess(deliveryString, MediaType.APPLICATION_JSON));

        MvcResult responseSave = mockMvc.perform(post(URI_SAVE)
                        .content(deliveryString)
                        .contentType(MediaType.APPLICATION_JSON))
                //.andExpect(status().is2xxSuccessful())
                .andReturn();
        String savedResponse = responseSave.getResponse().getContentAsString();
        MvcResult responseFetch = mockMvc.perform(get(URI_FETCH_CUSTOMER_ID_ORDER_ID, 100, 10)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String fetchedResponse = responseFetch.getResponse().getContentAsString();

        Delivery savedDelivery = JsonUtility.readValue(savedResponse, Delivery.class);
        Delivery fetchedDelivery = JsonUtility.readValue(fetchedResponse, Delivery.class);
        Assertions.assertEquals(savedDelivery.getOrderId(), fetchedDelivery.getOrderId());
        Assertions.assertEquals(savedDelivery.getCustomerId(), fetchedDelivery.getCustomerId());
        Assertions.assertEquals(savedDelivery.getStoreId(), fetchedDelivery.getStoreId());
    }
}
