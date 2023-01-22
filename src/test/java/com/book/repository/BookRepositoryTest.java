package com.book.repository;

import com.book.constant.BookSellStatus;
import com.book.dto.BookFormDto;
import com.book.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("책 저장 테스트")
    public void createBookTest() {
        Book book = new Book();
        book.setTitle("작별인사");
        book.setImageSrc("test");
        book.setAuthor("김영하");
        book.setCount(100);
        book.setIsbn("1234324");
        book.setPrice(10000);
        book.setPublisher("길벗");
        book.setDescription("테스트 상품 상세 설명");
        book.setBookSellStatus(BookSellStatus.SELL);
        book.setRegTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());
        Book savedBook = bookRepository.save(book);
        System.out.println(savedBook.toString());
    }


    public void createBookList() {
        for(int i = 1; i<=10; i++) {
            Book book = new Book();
            book.setTitle("테스트 상품" + i);
            book.setPrice(10000 + i);
            book.setDescription("테스트 상품 상세 설명" + i);
            Book savedBook = bookRepository.save(book);
        }
    }

    @Test
    @DisplayName("책 제목 조회 테스트")
    public void findByItemNmTest() {
        this.createBookList();
        BookFormDto bookFormDto = bookRepository.findByTitle("테스트");
//        for(BookFormDto book : bookFormDto) {
//            System.out.println(book.toString());
//        }
        System.out.println(bookFormDto.getClass().getDeclaredFields().length);
//        System.out.println(bookFormDto.getTitle());
    }



//    @Test
//    @DisplayName("책 제목, 상세 설명 조회 테스트")
//    public void findByBookNmOrBookDetail() {
//        this.createBookList();
//        List<Book> bookList = bookRepository.findByTitleOrDescription("테스트 상품2", "테스트 상품 상세 설명2");
//        for(Book book : bookList) {
//            System.out.println(book.toString());
//        }
//    }
//
//    @Test
//    @DisplayName("가격 LessThan 테스트")
//    public void findByPriceLessThan() {
//        this.createBookList();
//        List<Book> bookList = bookRepository.findBypriceLessThan(10005);
//        for (Book book : bookList) {
//            System.out.println(book.toString());
//        }
//    }
//
//    @Test
//    @DisplayName("가격 내림차순 조회 테스트")
//    public void findByPriceLessThanOrderByPriceDesc() {
//        this.createBookList();
//        List<Book> bookList = bookRepository.findBypriceLessThanOrderBypriceDesc(10005);
//        for (Book book : bookList) {
//            System.out.println(book.toString());
//        }
//    }
//
//    @Test
//    @DisplayName("@Query를 이용한 상품 조회 테스트")
//    public void findByBookDetailTest() {
//        this.createBookList();
//        List<Book> bookList = bookRepository.findBypriceLessThanOrderBypriceDesc(10005);
//        for (Book book : bookList) {
//            System.out.println(book.toString());
//        }
//    }
}