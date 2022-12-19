package com.book.entity;

import com.book.dto.BookDto;
import com.book.dto.CartDto;
import com.book.dto.MemberFormDto;
import com.book.repository.BookRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;           //제목

    @Lob
    @Column(name = "img")
    private String imageSrc;        //첵 표지


    @Column(name = "price", nullable = false)
    private Integer discount;        //가격


    @Column(nullable = false)
    private Integer stockNumber;



//    public static Cart cartAddBook (Integer stockNumber, CartDto cartDto) {
//        Cart cart = new Cart();
//        cart.setTitle(String.valueOf(cartDto.getTitle()));
//        cart.setImageSrc(String.valueOf(cartDto.getImageSrc()));
//        cart.setDiscount(String.valueOf(cartDto.getDiscount()));
//        cart.setStockNumber(stockNumber);
//
//        return cart;
//    }

    public static Cart cartAddBook(CartDto cartDto) {
        Cart cart = new Cart();
        cart.setTitle(cartDto.getTitle());
        cart.setImageSrc(cartDto.getImageSrc());
        cart.setDiscount(cartDto.getDiscount());
        cart.setStockNumber(cartDto.getStockNumber());

        return cart;
    }
}
