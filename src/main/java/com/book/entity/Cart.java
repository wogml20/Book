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
@Table(name = "cart")
public class Cart extends BaseEntity{

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;



//    @Column(nullable = false, length = 200)
//    private String title;           //제목
//
//    @Lob
//    @Column(name = "img")
//    private String imageSrc;        //첵 표지
//
//
//    @Column(name = "price", nullable = false)
//    private Integer discount;        //가격
//
//




//    public static Cart cartAddBook (Integer stockNumber, CartDto cartDto) {
//        Cart cart = new Cart();
//        cart.setTitle(String.valueOf(cartDto.getTitle()));
//        cart.setImageSrc(String.valueOf(cartDto.getImageSrc()));
//        cart.setDiscount(String.valueOf(cartDto.getDiscount()));
//        cart.setStockNumber(stockNumber);
//
//        return cart;
//    }

    public static Cart cartAddBook(BookDto car) {
        Cart cart = new Cart();

        return cart;
    }
}
