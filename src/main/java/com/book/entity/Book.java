package com.book.entity;

import com.book.dto.BookDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;

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


    @Column(nullable = false, length = 200)
    private String title;           //제목


    private String link;            //네이버스토어 링크

    @Lob
    @Column(name = "img", unique = true)
    private String imageSrc;        //첵 표지


    private String author;          //작가


    private String isbn;            //isbn

    @Column(name = "price", nullable = false)
    private Integer discount;        //가격

    private String publisher;       // 출판사

    @Lob
    @Column(nullable = false, unique = true)
    private String description;     //상세 설명


    public static Book createBook (ArrayList<BookDto> bookDtos) {
        Book book = new Book();
//            book.setTitle(String.valueOf(bookDtos.getTitle()));
//            book.setLink(String.valueOf(bookDtos.getLink()));
//            book.setImageSrc(String.valueOf(bookDtos.getImageSrc()));
//            book.setAuthor(String.valueOf(bookDtos.getAuthor()));
//            book.setIsbn(String.valueOf(bookDtos.getIsbn()));
//            book.setDiscount((bookDtos.getDiscount()));
//            book.setPublisher(String.valueOf(bookDtos.getPublisher()));
//            book.setDescription(String.valueOf(bookDtos.getDescription()));

        for(int i = 0; i<bookDtos.size(); i++) {
            book.setTitle(String.valueOf(bookDtos.get(i).getTitle()));
            book.setLink(String.valueOf(bookDtos.get(i).getLink()));
            book.setImageSrc(String.valueOf(bookDtos.get(i).getImageSrc()));
            book.setAuthor(String.valueOf(bookDtos.get(i).getAuthor()));
            book.setIsbn(String.valueOf(bookDtos.get(i).getIsbn()));
            book.setDiscount((bookDtos.get(i).getDiscount()));
            book.setPublisher(String.valueOf(bookDtos.get(i).getPublisher()));
            book.setDescription(String.valueOf(bookDtos.get(i).getDescription()));

        }


        return book;
    }
}
