package com.surya.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {

    private ObjectId id;

    private String productId;

    private String productName;

    private long stockLeft;

    @Transient
    private long qty;

}
