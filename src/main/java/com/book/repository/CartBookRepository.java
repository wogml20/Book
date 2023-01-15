package com.book.repository;

import com.book.dto.CartDetailDto;
import com.book.entity.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartBookRepository extends JpaRepository<CartBook, Long> {

    CartBook findByCartIdAndBookId(Long cartId, Long bookId);

    @Query("select new com.book.dto.CartDetailDto(ci.id, i.title, i.discount, ci.stockNumber, i.imageSrc) " +
    "from CartBook ci " + "join ci.book i " +
    "where ci.cart.id = :cartId " +
    "order by ci.regTime desc")
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
