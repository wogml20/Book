package com.book.service;


import com.book.dto.BookFormDto;
import com.book.dto.BookSearchDto;
import com.book.dto.MainBookDto;
import com.book.entity.Book;
import com.book.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    
//    @Transactional
//    public List<Book> searchBooks(String title) {
//        List<Book> bookDtoList = bookRepository.findByTitleContaining(title);
//        return bookDtoList;
//    }

    @Transactional(readOnly = true)
    public BookFormDto searchBooks(String title) {
        Book book = (Book) bookRepository.findByTitle(title);
        log.info("========================================================");
        log.info(book);
        BookFormDto  bookFormDto = BookFormDto.of(book);
        log.info("========================================================");
        log.info(bookFormDto);
        return bookFormDto;
    }

    @Transactional(readOnly = true)
    public BookFormDto getBookDtl(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);
        BookFormDto bookFormDto = BookFormDto.of(book);
        return bookFormDto;
    }

    public Long updateBook(BookFormDto bookFormDto) throws Exception {
        Book book = bookRepository.findById(bookFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        book.updateBook(bookFormDto);
        return book.getId();
    }


    @Transactional(readOnly = true)
    public Page<Book> getAdminBookPage(BookSearchDto bookSearchDto, Pageable pageable) {
        return bookRepository.getAdminBookPage(bookSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainBookDto> getMainBookPage(BookSearchDto bookSearchDto, Pageable pageable) {
        return bookRepository.getMainBookPage(bookSearchDto, pageable);
    }
}
