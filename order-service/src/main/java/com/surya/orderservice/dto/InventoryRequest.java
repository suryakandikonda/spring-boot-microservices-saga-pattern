package com.surya.orderservice.dto;

public record InventoryRequest(String id, String productId, Long stock, Long quantity) {
}
