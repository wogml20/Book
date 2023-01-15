package com.book.entity;

import com.book.constant.BookSellStatus;
import com.book.dto.BookDto;
import com.book.dto.BookFormDto;
import com.book.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@Entity
@Table(name = "book")
public class Book extends BaseEntity{

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(nullable = false, length = 200)
    private String title;           //제목

//    private String link;            //네이버스토어

    @Lob
    @Column(name = "img", unique = true)
    private String imageSrc;        //첵 표지

    private String author;          //작가

    @Column(nullable = false)
    private int stockNumber;

    private String isbn;            //isbn

    @Column(name = "price", nullable = false)
    private int discount;        //가격

    private String publisher;       // 출판사

    @Lob
    @Column(nullable = false, unique = true)
    private String description;     //상세 설명

    @Enumerated(EnumType.STRING)
    private BookSellStatus bookSellStatus;

    public static Book createBook (ArrayList<BookDto> bookDtos) {
        Book book = new Book();

        for(int i = 0; i<bookDtos.size(); i++) {
            book.setTitle(String.valueOf(bookDtos.get(i).getTitle()));
            book.setImageSrc(String.valueOf(bookDtos.get(i).getImageSrc()));
            book.setAuthor(String.valueOf(bookDtos.get(i).getAuthor()));
            book.setIsbn(String.valueOf(bookDtos.get(i).getIsbn()));
            book.setDiscount((bookDtos.get(i).getDiscount()));
            book.setPublisher(String.valueOf(bookDtos.get(i).getPublisher()));
            book.setDescription(String.valueOf(bookDtos.get(i).getDescription()));

        }
        return book;
    }

    public void updateBook(BookFormDto bookFormDto) {
        this.title = bookFormDto.getTitle();
        this.imageSrc = bookFormDto.getImageSrc();
        this.discount = bookFormDto.getDiscount();
        this.author = bookFormDto.getAuthor();
        this.description = bookFormDto.getDescription();
        this.isbn = bookFormDto.getIsbn();
        this.bookSellStatus = bookFormDto.getBookSellStatus();
        this.publisher = bookFormDto.getPublisher();
        this.stockNumber = bookFormDto.getStockNumber();

    }

    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;
        if(restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
    }

    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }
}
