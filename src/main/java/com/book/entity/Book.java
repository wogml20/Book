package com.book.entity;

import com.book.dto.BookDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(nullable = false, length = 50)
//    private String bookNm;
//
//    @Column(name = "price", nullable = false)
//    private int price;
//
//    @Column(nullable = false)
//    private int stockNumber;
//
//    @Lob
//    @Column(nullable = false)
//    private String bookDetail;
//
//    @Enumerated(EnumType.STRING)
//    private BookSellStatus bookSellStatus;
//
//    private LocalDateTime regTime;
//
//    private LocalDateTime updateTime;

    @Column(nullable = false, length = 50)
    private String title;           //제목


    private String link;            //네이버스토어 링크

    @Lob
    @Column(name = "img")
    private String imageSrc;        //첵 표지


    private String author;          //작가


    private String isbn;            //isbn

    @Column(name = "price", nullable = false)
    private String discount;        //가격

    private String publisher;       // 출판사

    @Column(nullable = false)
    private Integer stockNumber;

    @Lob
    @Column(nullable = false)
    private String description;     //상세 설명


    public static Book createBook (Integer stockNumber, BookDto bookDtos) {
        Book book = new Book();

//        for(int i = 0; i<bookDtos.size(); i++) {
//            book.setTitle(String.valueOf(bookDtos.get(i).getTitle()));
//            book.setLink(String.valueOf(bookDtos.get(i).getLink()));
//            book.setImageSrc(String.valueOf(bookDtos.get(i).getImageSrc()));
//            book.setAuthor(String.valueOf(bookDtos.get(i).getAuthor()));
//            book.setIsbn(String.valueOf(bookDtos.get(i).getIsbn()));
//            book.setDiscount(String.valueOf(bookDtos.get(i).getDiscount()));
//            book.setPublisher(String.valueOf(bookDtos.get(i).getPublisher()));
//            book.setStockNumber(stockNumber);
//            book.setDescription(String.valueOf(bookDtos.get(i).getDescription()));
//        }

            book.setTitle(String.valueOf(bookDtos.getTitle()));
            book.setLink(String.valueOf(bookDtos.getLink()));
            book.setImageSrc(String.valueOf(bookDtos.getImageSrc()));
            book.setAuthor(String.valueOf(bookDtos.getAuthor()));
            book.setIsbn(String.valueOf(bookDtos.getIsbn()));
            book.setDiscount(String.valueOf(bookDtos.getDiscount()));
            book.setPublisher(String.valueOf(bookDtos.getPublisher()));
            book.setStockNumber(stockNumber);
            book.setDescription(String.valueOf(bookDtos.getDescription()));


        return book;
    }
}
