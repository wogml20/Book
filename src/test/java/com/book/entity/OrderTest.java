package com.book.entity;

import com.book.constant.BookSellStatus;
import com.book.repository.BookRepository;
import com.book.repository.MemberRepository;
import com.book.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BookRepository bookRepository;

    @PersistenceContext
    EntityManager em;

    public Book createBook() {
        Book book = new Book();
        book.setTitle("테스트 상품");
        book.setImageSrc("테스트");
        book.setAuthor("김영하");
        book.setIsbn("12343");
        book.setprice(1000);
        book.setPublisher("길벗");
        book.setDescription("테스트 상세설명");
        book.setBookSellStatus(BookSellStatus.SELL);
        book.setRegTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());

        return book;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {
        Order order = new Order();

        for(int i = 0; i<3; i++) {
            Book book = this.createBook();
            bookRepository.save(book);
            OrderBook orderItem = new OrderBook();
            orderItem.setBook(book);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        orderRepository.saveAndFlush(order);
        em.clear();

        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size());
    }

    @Autowired
    MemberRepository memberRepository;

    public Order createOrder() {
        Order order = new Order();

        for(int i = 0; i<3; i++) {
            Book book = this.createBook();
            bookRepository.save(book);
            OrderBook orderItem = new OrderBook();
            orderItem.setBook(book);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest() {
        Order order = this.createOrder();
        order.getOrderItems().remove(0);
        em.flush();
    }

}