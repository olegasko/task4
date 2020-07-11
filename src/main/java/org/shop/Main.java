package org.shop;

import org.shop.db.OrdersRepository;
import org.shop.db.entity.OrderDetailEntity;
import org.shop.db.entity.OrderEntity;
import org.shop.dto.OrderDetailDto;
import org.shop.dto.OrderDto;
import org.shop.service.OrdersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);
        OrdersService ordersService = context.getBean(OrdersService.class);
        ordersService.findOrderBy(1);
        ordersService.deleteOrder(4);
        List<OrderDetailDto> detailDtos = List.of(OrderDetailDto.builder().name("Lenovo").price(7.345).build());
        ordersService.saveOrder(OrderDto
                .builder()
                .id(1)
                .name("Petr")
                .orderDetails(detailDtos)
                .build());
        System.out.println(ordersService.findAll());
    }

}
