import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shop.SpringContext;
import org.shop.dto.OrderDetailDto;
import org.shop.dto.OrderDto;
import org.shop.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContext.class})
public class OrderServiceImplTest {

    public static final String TEST_NAME = "Test Name";
    public static final OrderDetailDto TEST_DETAIL_1 = OrderDetailDto.builder().price(8.5).build();
    public static final OrderDetailDto TEST_DETAIL_2 = OrderDetailDto.builder().price(7).build();
    public static final OrderDetailDto TEST_DETAIL_3 = OrderDetailDto.builder().price(5.01).build();
    public static final OrderDetailDto TEST_DETAIL_4 = OrderDetailDto.builder().price(5).build();
    public static final OrderDetailDto TEST_DETAIL_5 = OrderDetailDto.builder().price(3).build();
    public static final OrderDetailDto TEST_DETAIL_6 = OrderDetailDto.builder().price(3).build();
    public static final OrderDetailDto TEST_DETAIL_7 = OrderDetailDto.builder().price(3).build();
    public static final OrderDetailDto TEST_DETAIL_8 = OrderDetailDto.builder().price(3).build();
    public static final OrderDetailDto TEST_DETAIL_9 = OrderDetailDto.builder().price(3).build();

    @Autowired
    OrdersService ordersService;

    @Before
    public void before() {
        List<OrderDto> orders = ordersService.findAll();
        orders.forEach(order -> ordersService.deleteOrder(order.getId()));
    }

    @Test
    public void saveShouldAddOneRecord() {
        OrderDetailDto testDetail = OrderDetailDto.builder().build();
        OrderDto testOrder = OrderDto.builder().name("Test Name").orderDetails(List.of(testDetail)).build();
        ordersService.saveOrder(testOrder);
        Assertions.assertThat(ordersService.findAll().stream().map(OrderDto::getName)).contains(testOrder.getName());
    }

    @Test
    public void findOrdersWithPriceGreaterThenShouldReturn3Records() {
        OrderDto testOrder1 = OrderDto.builder().name(TEST_NAME).orderDetails(List.of(TEST_DETAIL_1)).build();
        OrderDto testOrder2 = OrderDto.builder().name(TEST_NAME).orderDetails(List.of(TEST_DETAIL_2)).build();
        OrderDto testOrder3 = OrderDto.builder().name(TEST_NAME).orderDetails(List.of(TEST_DETAIL_3)).build();
        OrderDto testOrder4 = OrderDto.builder().name(TEST_NAME).orderDetails(List.of(TEST_DETAIL_4)).build();
        OrderDto testOrder5 = OrderDto.builder().name(TEST_NAME).orderDetails(List.of(TEST_DETAIL_5)).build();
        ordersService.saveOrder(testOrder1);
        ordersService.saveOrder(testOrder2);
        ordersService.saveOrder(testOrder3);
        ordersService.saveOrder(testOrder4);
        ordersService.saveOrder(testOrder5);
        Assertions.assertThat(ordersService.findOrdersWithPriceGreaterThen(5).size()).isEqualTo(3);
    }

    @Test
    public void findOrderPriceShouldReturnSumPricesDetails() {
        OrderDto testOrder1 = OrderDto.builder().name(TEST_NAME).orderDetails(List.of(TEST_DETAIL_1, TEST_DETAIL_2)).build();
        long id = ordersService.saveOrder(testOrder1);
        Assertions.assertThat(ordersService.findOrderPrice(id)).isEqualTo(TEST_DETAIL_1.getPrice() + TEST_DETAIL_2.getPrice());
    }

    @Test
    public void findBigOrdersShouldOrdersWithMoreThenThreeDetails() {
        OrderDto testOrder1 = OrderDto
                .builder()
                .name(TEST_NAME)
                .orderDetails(List.of(TEST_DETAIL_1, TEST_DETAIL_2, TEST_DETAIL_3, TEST_DETAIL_4))
                .build();
        OrderDto testOrder2 = OrderDto
                .builder()
                .name(TEST_NAME)
                .orderDetails(List.of(TEST_DETAIL_5, TEST_DETAIL_6, TEST_DETAIL_7, TEST_DETAIL_8))
                .build();
        OrderDto testOrder3 = OrderDto
                .builder()
                .name(TEST_NAME)
                .orderDetails(List.of(TEST_DETAIL_9))
                .build();
        long id1 = ordersService.saveOrder(testOrder1);
        long id2 = ordersService.saveOrder(testOrder2);
        long id3 = ordersService.saveOrder(testOrder3);
        Assertions.assertThat(ordersService.findBigOrders().stream().map(OrderDto::getId))
                .contains(id1, id2);
    }
}
