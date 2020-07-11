package org.shop.db.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.shop.dto.OrderDto;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
public class OrderEntity {
    private long id;
    private String name;
    private String client;
    private List<OrderDetailEntity> orderDetailEntities;

    public OrderEntity(long id, String name, String client, List<OrderDetailEntity> orderDetailEntities) {
        this.id = id;
        this.name = name;
        this.client = client;
        this.orderDetailEntities = orderDetailEntities;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClient() {
        return client;
    }

    public List<OrderDetailEntity> getOrderDetailEntities() {
        return orderDetailEntities;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", client='" + client + '\'' +
                ", orderDetailEntities=" + orderDetailEntities +
                '}';
    }

    public OrderDto convert() {
        return OrderDto
                .builder()
                .id(this.getId())
                .name(this.getName())
                .orderDetails(this.getOrderDetailEntities().stream().map(OrderDetailEntity::convert).collect(Collectors.toList()))
                .build();
    }
}
