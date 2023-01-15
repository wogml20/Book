package com.book.dto;


import com.book.constant.BookSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchDto {

    private String searchDateType;

    private BookSellStatus searchSellStatus;

    private String searchBy;

    private String searchQuery ="";

}
