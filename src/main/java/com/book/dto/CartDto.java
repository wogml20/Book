package com.book.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CartDto {

//    private String title;           //제목
//    private String imageSrc;        //첵 표지
//    private Integer price;        //가격
//    private Integer stockNumber;        //재고


    @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
    private Long id;

    private String title;           //제목
    private String link;            //네이버스토어 링크
    private String imageSrc;        //첵 표지
    private String author;          //작가
    private String isbn;            //isbn
    private int price;        //가격
    private String publisher;       // 출판사
    private String description;     //상세 설명

    @Min(value=1, message = "최소 1개 이상 담아주세요")
    private int count;
}

