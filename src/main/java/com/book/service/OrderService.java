package com.book.service;

import com.book.dto.OrderBookDto;
import com.book.dto.OrderDto;
import com.book.dto.OrderHistDto;
import com.book.entity.Book;
import com.book.entity.Member;
import com.book.entity.Order;
import com.book.entity.OrderBook;
import com.book.repository.BookRepository;
import com.book.repository.MemberRepository;
import com.book.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String userid) {
        Book book = bookRepository.findById(orderDto.getBookId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByUserid(userid);


        List<OrderBook> orderBookList = new ArrayList<>();
        OrderBook orderBook =
                OrderBook.createOrderBook(book, orderDto.getCount());
        orderBookList.add(orderBook);

        Order order = Order.createOrder(member, orderBookList);
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String userid, Pageable pageable) {
        List<Order> orders = orderRepository.findOrders(userid,pageable);
        Long totalCount = orderRepository.countOrder(userid);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for(Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderBook> orderBooks = order.getOrderBooks();
            for(OrderBook orderBook : orderBooks) {
                OrderBookDto orderBookDto = new OrderBookDto(orderBook);
                orderHistDto.addOrderBookDto(orderBookDto);
            }

            orderHistDtos.add(orderHistDto);
        }

        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String userid) {
        Member curMember = memberRepository.findByUserid(userid);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        if(!StringUtils.equals(curMember.getUserid(), savedMember.getUserid())) {
            return false;
        }

        return true;
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

    public Long orders(List<OrderDto> orderDtoList, String email){

        Member member = memberRepository.findByEmail(email);
        List<OrderBook> orderBookList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {
            Book book = bookRepository.findById(orderDto.getBookId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderBook orderBook = OrderBook.createOrderBook(book, orderDto.getCount());
            orderBookList.add(orderBook);
        }

        Order order = Order.createOrder(member, orderBookList);
        orderRepository.save(order);

        return order.getId();
    }
}

