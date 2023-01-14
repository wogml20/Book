package com.book.entity;

import lombok.*;
import org.thymeleaf.standard.inline.StandardHTMLInliner;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private int count;

//    public static CartItem createCartItem(Cart cart, int count, String title, String link, String imageSrc, String author, String isbn, Integer discount, String publisher, String description ) {
//        CartItem cartItem = new CartItem();
//        cartItem.setCart(cart);
//        cartItem.setCount(count);
//        cartItem.setTitle(title);
//        cartItem.setLink(link);
//        cartItem.setImageSrc(imageSrc);
//        cartItem.setAuthor(author);
//        cartItem.setIsbn(isbn);
//        cartItem.setPublisher(publisher);
//        cartItem.setDescription(description);
//        cartItem.setDiscount(discount);
//
//        return cartItem;
//    }
//
//    public void addCound(int count) {
//        this.count += count;
//    }
//
//    public void updateCount(int count) {
//        this.count = count;
//    }
}
