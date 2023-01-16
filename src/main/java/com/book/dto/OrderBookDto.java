package com.book.dto;

import com.book.entity.OrderBook;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBookDto {

    private String title;           //제목
    private String imageSrc;        //첵 표지
    private int count;              //주문 수량
    private int orderPrice;        //가격

    public OrderBookDto (OrderBook orderBook) {
        this.title = orderBook.getBook().getTitle();
        this.count = orderBook.getCount();
        this.orderPrice = orderBook.getOrderPrice();
        this.imageSrc = orderBook.getBook().getImageSrc();
    }
}
