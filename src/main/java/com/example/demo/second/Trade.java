package com.example.demo.second;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Event
 */
@Data
@NoArgsConstructor
@ToString
public class Trade {
    private String id;
    private String name;
    private double price;
    private AtomicInteger count =new AtomicInteger(0);
}
