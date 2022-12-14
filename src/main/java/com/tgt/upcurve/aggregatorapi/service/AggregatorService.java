package com.tgt.upcurve.aggregatorapi.service;

import com.tgt.upcurve.aggregatorapi.model.Delivery;
import com.tgt.upcurve.aggregatorapi.model.Image;
import com.tgt.upcurve.aggregatorapi.model.ImageRequest;
import com.tgt.upcurve.aggregatorapi.model.Order;
import com.tgt.upcurve.aggregatorapi.repository.DeliveryRepository;
import com.tgt.upcurve.aggregatorapi.repository.ImageRepository;
import com.tgt.upcurve.aggregatorapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AggregatorService {

    private OrderRepository orderRepository;
    private ImageRepository imageRepository;
    private DeliveryRepository deliveryRepository;

    @Value("${apis.delivery}")
    private String deliveryApiURL;

    public AggregatorService(OrderRepository orderRepository,
                             ImageRepository imageRepository, DeliveryRepository deliveryRepository) {
        this.orderRepository = orderRepository;
        this.imageRepository = imageRepository;
        this.deliveryRepository = deliveryRepository;
    }

    public List<Order> getOrdersByCustomerId(Integer customerId) {
        return orderRepository.getOrdersByCustomerId(customerId);
    }

    public Delivery generateQRCode(Integer orderId, Integer customerId) {
        Delivery delivery = deliveryRepository.getDeliveryInfoByCustomerIdAndOrderId(customerId, orderId);
        if (null == delivery) {
            Order order = orderRepository.getOrderByCustomerIdAndOrderId(customerId, orderId);
            String content = deliveryApiURL + "/guest_arrival/order_id/" + orderId + "/customer_id/" + customerId;
            ImageRequest imageRequest = new ImageRequest();
            imageRequest.setContent(content);
            Image image = imageRepository.generateImageByContent(imageRequest);
            delivery = new Delivery();
            delivery.setCustomerId(order.getCustomerId());
            delivery.setStoreId(order.getStoreId());
            delivery.setOrderId(order.getOrderId());
            delivery.setPaymentStatus(order.getPaymentStatus());
            delivery.setDeliveryStatus("NOT-DELIVERED");
            delivery.setImageId(image.getId());
            delivery.setImageCode(image.getImageCode());
            delivery = deliveryRepository.saveDelivery(delivery);
        }
        return delivery;
    }

    public Delivery confirmDelivery(Integer orderId, Integer customerId) {
        Delivery delivery = deliveryRepository.getDeliveryInfoByCustomerIdAndOrderId(customerId, orderId);
        delivery.setDeliveryStatus("DELIVERED");
        delivery.setPickupDate(LocalDateTime.now());
        return deliveryRepository.saveDelivery(delivery);
    }

    public Boolean informCustomerArrival(Integer orderId, Integer customerId) {
        // TODO : Send Message to the Stores Team on Guest Arrival
        // Post to this parcel can be sent via the conveyor belt
        // Assume that, After sending the parcel via conveyor belt, the Stores Team system send True
//        System.out.println("Customer has arrived OrderId" + orderId + " Customer Id " + customerId);
        return true;
    }
}
