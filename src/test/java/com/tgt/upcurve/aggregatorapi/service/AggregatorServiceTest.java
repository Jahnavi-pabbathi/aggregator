package com.tgt.upcurve.aggregatorapi.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tgt.upcurve.aggregatorapi.model.Delivery;
import com.tgt.upcurve.aggregatorapi.model.Image;
import com.tgt.upcurve.aggregatorapi.model.ImageRequest;
import com.tgt.upcurve.aggregatorapi.model.Order;
import com.tgt.upcurve.aggregatorapi.repository.DeliveryRepository;
import com.tgt.upcurve.aggregatorapi.repository.ImageRepository;
import com.tgt.upcurve.aggregatorapi.repository.OrderRepository;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AggregatorService.class})
@ExtendWith(SpringExtension.class)
public class AggregatorServiceTest {

    @Autowired
    private AggregatorService aggregatorService;

    @MockBean
    private DeliveryRepository deliveryRepository;

    @MockBean
    private ImageRepository imageRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    void testGetOrdersByCustomerId() {
        ArrayList<Order> orderList = new ArrayList<>();
        when(orderRepository.getOrdersByCustomerId((Integer) any())).thenReturn(orderList);
        List<Order> actualOrdersByCustomerId = aggregatorService.getOrdersByCustomerId(123);
        assertSame(orderList, actualOrdersByCustomerId);
        assertTrue(actualOrdersByCustomerId.isEmpty());
        verify(orderRepository).getOrdersByCustomerId((Integer) any());
    }

    @Test
    void testGenerateQRCode() throws UnsupportedEncodingException {
        Order order = new Order();
        order.setCustomerEmail("jane.doe@example.org");
        order.setCustomerId(123);
        order.setCustomerMobile("Customer Mobile");
        order.setId(1);
        order.setOrderAmount(10.0f);
        order.setOrderId(123);
        order.setOrderItems(new ArrayList<>());
        order.setOrderStatus("Order Status");
        order.setPaymentStatus("Payment Status");
        order.setStoreId(123);
        when(orderRepository.getOrderByCustomerIdAndOrderId((Integer) any(), (Integer) any())).thenReturn(order);
        when(imageRepository.generateImageByContent((ImageRequest) any())).thenReturn(new Image());

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

        Delivery delivery1 = new Delivery();
        delivery1.setCustomerId(123);
        delivery1.setDeliveryStatus("Delivery Status");
        delivery1.setId(1);
        delivery1.setImageCode("AAAAAAAA".getBytes("UTF-8"));
        delivery1.setImageId(123L);
        delivery1.setOrderId(123);
        delivery1.setPaymentStatus("Payment Status");
        delivery1.setPickupDate(LocalDateTime.of(1, 1, 1, 1, 1));
        delivery1.setStoreId(123);
        when(deliveryRepository.getDeliveryInfoByCustomerIdAndOrderId((Integer) any(), (Integer) any()))
                .thenReturn(delivery);
        when(deliveryRepository.saveDelivery((Delivery) any())).thenReturn(delivery1);
        assertSame(delivery, aggregatorService.generateQRCode(123, 123));
        verify(deliveryRepository).getDeliveryInfoByCustomerIdAndOrderId((Integer) any(), (Integer) any());
    }

    @Test
    void testConfirmDelivery() throws UnsupportedEncodingException {
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

        Delivery delivery1 = new Delivery();
        delivery1.setCustomerId(123);
        delivery1.setDeliveryStatus("Delivery Status");
        delivery1.setId(1);
        delivery1.setImageCode("AAAAAAAA".getBytes("UTF-8"));
        delivery1.setImageId(123L);
        delivery1.setOrderId(123);
        delivery1.setPaymentStatus("Payment Status");
        delivery1.setPickupDate(LocalDateTime.of(1, 1, 1, 1, 1));
        delivery1.setStoreId(123);
        when(deliveryRepository.saveDelivery((Delivery) any())).thenReturn(delivery1);
        when(deliveryRepository.getDeliveryInfoByCustomerIdAndOrderId((Integer) any(), (Integer) any()))
                .thenReturn(delivery);
        assertSame(delivery1, aggregatorService.confirmDelivery(123, 123));
        verify(deliveryRepository).getDeliveryInfoByCustomerIdAndOrderId((Integer) any(), (Integer) any());
        verify(deliveryRepository).saveDelivery((Delivery) any());
    }

    @Test
    void testInformCustomerArrival() {
        assertTrue(aggregatorService.informCustomerArrival(123, 123));
    }
}
