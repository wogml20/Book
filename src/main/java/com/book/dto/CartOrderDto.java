package com.book.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {

    private Long cartBookId;

    private List<CartOrderDto> cartOrderDtoList;
}
