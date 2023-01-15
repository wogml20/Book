package com.book.repository;

import com.book.dto.CartDetailDto;
import com.book.entity.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartBookRepository extends JpaRepository<CartBook, Long> {

    CartBook findByCartIdAndBookId(Long cartId, Long bookId);

    @Query("select  new com.book.dto.CartDetailDto(ci.id, ci.book.title, ci.book.discount, ci.book.stockNumber, ci.book.imageSrc) " +
    "from CartBook ci " +
    "where ci.cart.id = :cartId " +
    "order by ci.regTime desc")
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
