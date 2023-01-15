package com.book.entity;


import com.book.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderBook> orderBooks = new ArrayList<>();

    public void addOrderBook(OrderBook orderBook) {
        orderBooks.add(orderBook);
        orderBook.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderBook> orderBookList) {
        Order order = new Order();
        order.setMember(member);
        for(OrderBook orderBook : orderBookList) {
            order.addOrderBook(orderBook);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    public int getTotalDiscount() {
        int totalPrice = 0;
        for(OrderBook orderBook : orderBooks) {
            totalPrice += orderBook.getTotalDiscount();
        }
        return totalPrice;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderBook orderBook : orderBooks) {
            orderBook.cancel();
        }
    }

}
