package com.book.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderBook extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private Integer count;

    public static OrderBook createOrderBook(Book book, int count) {
        OrderBook orderBook = new OrderBook();
        orderBook.setBook(book);
        orderBook.setCount(count);
        orderBook.setOrderPrice(book.getDiscount());

        book.removeStock(count);
        return orderBook;
    }

    public int getTotalDiscount() {
        return orderPrice * count;
    }

    public void cancel() {
        this.getBook().addStock(count);
    }
}
