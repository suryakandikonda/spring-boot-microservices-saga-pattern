package com.surya.microservices.dto.Inventory;

public record InventoryResponse(String productId, String productName, long stockLeft) {
}
