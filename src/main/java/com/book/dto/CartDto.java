package com.book.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CartDto {

    private String title;           //제목
    private String imageSrc;        //첵 표지
    private Integer discount;        //가격
    private Integer stockNumber;        //재고

}

