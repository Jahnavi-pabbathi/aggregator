package com.tgt.upcurve.aggregatorapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItems {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("order_id")
    private Integer orderId;

    @JsonProperty("item_id")
    private Integer itemId;

    @JsonProperty("item_price")
    private Float itemPrice;

    @JsonProperty("item_description")
    private String itemDescription;

    @JsonProperty("item_quantity")
    private Integer itemQuantity;

}
