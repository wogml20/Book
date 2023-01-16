package com.book.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDto {

    private Long cartBookId;

    private String title;

    private Integer discount;

    private Integer stockNumber;

    private String imageSrc;

    public CartDetailDto(Long cartBookId, String title, Integer discount, Integer stockNumber, String imageSrc) {
        this.cartBookId = cartBookId;
        this.title = title;
        this.discount = discount;
        this.stockNumber = stockNumber;
        this.imageSrc = imageSrc;
    }
}
