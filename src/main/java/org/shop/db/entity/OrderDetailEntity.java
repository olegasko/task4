package org.shop.db.entity;


import lombok.Builder;
import org.shop.dto.OrderDetailDto;

@Builder
public class OrderDetailEntity {
    private long id;
    private String name;
    private double price;

    public OrderDetailEntity(long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "OrderDetailEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public OrderDetailDto convert() {
        return OrderDetailDto
                .builder()
                .id(this.getId())
                .name(this.getName())
                .price(this.getPrice())
                .build();
    }
}
