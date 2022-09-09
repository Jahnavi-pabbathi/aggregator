package com.tgt.upcurve.aggregatorapi.repository;

import com.tgt.upcurve.aggregatorapi.model.Image;
import com.tgt.upcurve.aggregatorapi.utility.JsonUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ImageRepositoryTest {
    @Autowired
    MockMvc mockMvc;

    private static final String URI_GET_ORDER_ID_CUSTOMER_ID = "/image_api/v1/generate_image/order_id/{order_id}/customer_id/{customer_id}";
    private static final String IMAGE_JSON_FILE_PATH = "/imageData.json";

    @Test
    public void testGenerateImage() throws Exception {
        MvcResult responseFetch = mockMvc.perform(get(URI_GET_ORDER_ID_CUSTOMER_ID, 1,1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String fetchedResponse = responseFetch.getResponse().getContentAsString();
        Image fetchedImage = JsonUtility.readValue(fetchedResponse, Image.class);
        Assertions.assertEquals(fetchedImage.getId(), fetchedImage.getId());
        Assertions.assertNotNull(fetchedImage.getImageCode());
    }
}
