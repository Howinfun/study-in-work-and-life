package com.hyf.facebook.demo.conversions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * @author winfun
 * @date 2021/2/1 3:35 下午
 **/
@Data
public class CustomData {

    /**
     * A numeric value associated with this event. This could be a monetary value or a value in some other metric. Example: 142.54.
     */
    private Float value;
    /**
     * The currency for the value specified, if applicable. Currency must be a valid ISO 4217 three digit currency
     * code. Example: 'USD'.
     */
    private String currency;
    /**
     * The name of the page or product associated with the event. Example: 'lettuce'.
     */
    @JsonProperty("content_name")
    private String contentName;
    /**
     * The category of the content associated with the event. Example: 'grocery'.
     */
    @JsonProperty("content_category")
    private String contentCategory;
    /**
     * The content IDs associated with the event, such as product SKUs for items in an AddToCart event: ['ABC123', 'XYZ789']. If content_type is a product, then your content IDs must be an array with a single string value. Otherwise, this array can contain any number of string values.
     */
    @JsonProperty("content_ids")
    private List<String> contentIds;
    /**
     * A list of JSON objects that contain the product IDs associated with the event plus information about the products. id, quantity, and item_price are available fields.
     */
    private List<Content> contents;
    /**
     * It should be set to product or product_group:
     */
    @JsonProperty("content_type")
    private String contentType;
    /**
     * The order ID for this transaction as a String. Example: 'order1234'.
     */
    @JsonProperty("order_id")
    private String orderId;
    /**
     * The predicted lifetime value of a conversion event. Example: 432.12.
     */
    @JsonProperty("predicted_ltv")
    private Float predictedLtv;
    /**
     * Use only with InitiateCheckout events. The number of items that a user tries to buy during checkout. Example:
     * '4'.
     */
    @JsonProperty("num_items")
    private String numItems;
    /**
     * Use only with Search events. A search query made by a user. Example: 'lettuce'.
     */
    @JsonProperty("search_string")
    private String searchString;
    /**
     * Use only with CompleteRegistration events. The status of the registration event, as a String. Example:
     * 'registered'.
     */
    private String status;
    /**
     * Type of delivery for a purchase event. Supported values are:
     *
     * in_store: Customer needs to enter the store to get the purchased product.
     * curbside: Customer picks up their order by driving to a store and waiting inside their vehicle.
     * home_delivery: Purchase is delivered to the customer's home.
     */
    @JsonProperty("delivery_category")
    private String deliveryCategory;

    @Data
    static class Content{
        private String id;
        private Integer quantity;
        @JsonProperty("item_price")
        private Float itemPrice;
    }
}
