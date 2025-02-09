package com.surya.microservices.dto.Inventory;

public record InventoryRequest(String productId, String productName, long qty) {
}
