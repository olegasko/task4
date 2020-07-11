package org.shop.dto;

import lombok.Builder;
import lombok.Getter;
import org.shop.db.entity.OrderDetailEntity;
import org.shop.db.entity.OrderEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * feel free to add any code to this class
 */
@Builder
@Getter
public class OrderDto {
    private long id;
    private String name;
    private List<OrderDetailDto> orderDetails;

    public OrderEntity convert() {
        return OrderEntity
                .builder()
                .id(this.id)
                .name(this.name)
                .orderDetailEntities(this.orderDetails.stream().map(OrderDetailDto::convert).collect(Collectors.toList()))
                .build();
    }
}
