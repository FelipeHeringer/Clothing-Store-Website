package com.fhcs.clothing_store.dto.response.order;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderResponse {

    private boolean success;
    private String message;
    private OrderDto order;
    private List<OrderDto> orders;

    public static OrderResponse success(OrderDto order, String message) {
        return OrderResponse.builder().success(true).message(message).order(order).build();
    }

    public static OrderResponse successList(List<OrderDto> orders, String message) {
        return OrderResponse.builder().success(true).message(message).orders(orders).build();
    }

    public static OrderResponse error(String message) {
        return OrderResponse.builder().success(false).message(message).build();
    }
}