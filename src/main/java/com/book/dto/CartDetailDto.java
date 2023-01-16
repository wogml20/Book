package com.book.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDto {

    private Long cartBookId;

    private String title;

    private int price;

    private int count;

    private String imageSrc;

    public CartDetailDto(Long cartBookId, String title, int price, int count, String imageSrc) {
        this.cartBookId = cartBookId;
        this.title = title;
        this.price = price;
        this.count = count;
        this.imageSrc = imageSrc;
    }
}
