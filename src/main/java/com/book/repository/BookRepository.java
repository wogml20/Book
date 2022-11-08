package com.book.repository;

import com.book.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitle(String title);
    List<Book> findByTitleOrDescription(String title, String description);
    List<Book> findByDiscountLessThan(Integer discount);
    List<Book> findByDiscountLessThanOrderByDiscountDesc(Integer discount);

    List<Book> findAll();

    @Query("select i from Book i where i.description like %:description% order by i.discount desc")
    List<Book> findByDescription(@Param("description") String description);
}
