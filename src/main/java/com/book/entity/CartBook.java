package com.book.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "cart_book")
public class CartBook extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "cart_book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private int stockNumber;

    public static CartBook createCartBook(Cart cart, Book book, int stockNumber) {
        CartBook cartBook = new CartBook();
        cartBook.setCart(cart);
        cartBook.setBook(book);
        cartBook.setStockNumber(stockNumber);
        return cartBook;
    }

    public void addCount(int stockNumber) {
        this.stockNumber += stockNumber;
    }

    public void updateCount(int stockNumber) {
        this.stockNumber = stockNumber;
    }
}
