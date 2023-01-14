package com.book.service;


import com.book.dto.BookFormDto;
import com.book.entity.Book;
import com.book.repository.BookRepository;
import com.book.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Long saveBook(BookFormDto bookFormDto) {
        Book book = bookFormDto.createBook();
        bookRepository.save(book);

        return book.getId();
    }
    
    @Transactional
    public List<Book> searchBooks(String title) {
        List<Book> bookDtoList = bookRepository.findByTitleContaining(title);
        return bookDtoList;
    }

}
