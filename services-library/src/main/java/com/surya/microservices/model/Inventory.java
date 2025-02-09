package com.surya.microservices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {

    private ObjectId id;

    private String productId;

    private String productName;

    private long stockLeft;

}
