package com.book.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDto {

    private Long cartBookId;

    private String title;

    private int discount;

    private int stockNumber;

    private String imageSrc;

    public CartDetailDto(Long cartBookId, String title, int discount, int stockNumber, String imageSrc) {
        this.cartBookId = cartBookId;
        this.title = title;
        this.discount = discount;
        this.stockNumber = stockNumber;
        this.imageSrc = imageSrc;
    }
}
