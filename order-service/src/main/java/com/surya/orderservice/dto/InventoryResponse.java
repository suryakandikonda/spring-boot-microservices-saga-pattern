package com.surya.orderservice.dto;

public record InventoryResponse(String id, String productId, Long stock, Long reserved) {
}
