package com.book.repository;

import com.book.constant.BookSellStatus;
import com.book.dto.BookSearchDto;
import com.book.dto.MainBookDto;
import com.book.dto.QMainBookDto;
import com.book.entity.Book;
import com.book.entity.QBook;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public BookRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(BookSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QBook.book.bookSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QBook.book.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("title", searchBy)) {
            return QBook.book.title.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)) {
            return QBook.book.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Book> getAdminBookPage(BookSearchDto bookSearchDto, Pageable pageable) {
        QueryResults<Book> results = queryFactory
                .selectFrom(QBook.book)
                .where(regDtsAfter(bookSearchDto.getSearchDateType()),
                        searchSellStatusEq(bookSearchDto.getSearchSellStatus()),
                        searchByLike(bookSearchDto.getSearchBy(),
                                bookSearchDto.getSearchQuery()))
                .orderBy(QBook.book.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Book> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);
    }

    private BooleanExpression titleLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QBook.book.title.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainBookDto> getMainBookPage(BookSearchDto bookSearchDto, Pageable pageable) {
        QBook book = QBook.book;

        QueryResults<MainBookDto> results = queryFactory
                .select(
                        new QMainBookDto(
                                book.id,
                                book.title,
                                book.description,
                                book.imageSrc,
                                book.author,
                                book.isbn,
                                book.publisher,
                                book.discount)
                )
                .from(book)
                .where(titleLike((bookSearchDto.getSearchQuery())))
                .orderBy(book.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainBookDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);

    }
}
