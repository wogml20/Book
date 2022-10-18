package com.book.repository;

import com.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByBookNm(String bookNm);
    List<Book> findByBookNmOrBookDetail(String bookNm, String bookDetail);
    List<Book> findByPriceLessThan(Integer price);
    List<Book> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Book i where i.bookDetail like %:bookDetail% order by i.price desc")
    List<Book> findByBookDetail(@Param("bookDetail") String bookDetail);
}
