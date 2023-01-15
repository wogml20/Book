package com.book.repository;

import com.book.dto.BookSearchDto;
import com.book.dto.MainBookDto;
import com.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {

    Page<Book> getAdminBookPage(BookSearchDto bookSearchDto, Pageable pageable);

    Page<MainBookDto> getMainBookPage(BookSearchDto bookSearchDto, Pageable pageable);
}
