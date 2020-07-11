package org.shop.dto;

import lombok.Builder;
import lombok.Getter;
import org.shop.db.entity.OrderDetailEntity;

/**
 * feel free to add any code to this class
 */
@Builder
@Getter
public class OrderDetailDto {
    private long id;
    private String name;
    private double price;

    public OrderDetailEntity convert() {
        return OrderDetailEntity
                .builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .build();
    }
}
