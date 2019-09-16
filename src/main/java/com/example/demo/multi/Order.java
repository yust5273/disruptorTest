package com.example.demo.multi;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Order {
    private String id;
    private String name;
    private double price;
}
