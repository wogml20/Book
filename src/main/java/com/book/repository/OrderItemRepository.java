package com.book.repository;

import com.book.entity.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderBook, Long> {
}
