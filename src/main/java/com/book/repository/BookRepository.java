package com.book.repository;

import com.book.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book>, BookRepositoryCustom {

    List<Book> findByTitle(String title);

    List<Book> findByTitleContaining(String title);

    List<Book> findAll();

    @Query("select i from Book i where i.description like %:description% order by i.price desc")
    List<Book> findByDescription(@Param("description") String description);
}
