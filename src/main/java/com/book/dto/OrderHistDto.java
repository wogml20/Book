package com.book.dto;

import com.book.constant.OrderStatus;
import com.book.entity.Order;
import com.book.entity.OrderBook;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

    private Long orderId;

    private String orderDate;

    private OrderStatus orderStatus;

    private List<OrderBookDto> orderBookDtoList = new ArrayList<>();

    public OrderHistDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    public void addOrderBookDto(OrderBookDto orderBookDto) {
        orderBookDtoList.add(orderBookDto);
    }
}
