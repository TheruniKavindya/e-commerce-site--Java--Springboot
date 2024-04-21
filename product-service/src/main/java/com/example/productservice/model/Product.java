package com.example.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "product") // to define the product as a mongodb document
@AllArgsConstructor
@NoArgsConstructor
@Builder // to create the builder method
@Data // to get the getters and setters
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
