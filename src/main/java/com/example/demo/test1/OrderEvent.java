package com.example.demo.test1;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务实体类，这个类 只走内存， 不准备走IO，所以不实现 序列化接口了
 */
@Data
@NoArgsConstructor
public class OrderEvent {
    private long value;

}
