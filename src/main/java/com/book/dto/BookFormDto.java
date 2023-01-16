package com.book.dto;

import com.book.constant.BookSellStatus;
import com.book.entity.Book;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class BookFormDto {

    private Long id;

    private String title;           //제목

    private String imageSrc;        //첵 표지

    private String author;          //작가

    private int count;

    private String isbn;            //isbn

    private int price;        //가격

    private String publisher;       // 출판사

    private String description;     //상세 설명

    private BookSellStatus bookSellStatus;

    public static ModelMapper modelMapper = new ModelMapper();

    public Book createBook() {
        return modelMapper.map(this, Book.class);
    }

    public static BookFormDto of(Book book) {
        return modelMapper.map(book, BookFormDto.class);
    }

}
