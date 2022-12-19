package com.book.service;


import com.book.entity.Book;
import com.book.entity.Cart;
import com.book.repository.BookRepository;
import com.book.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class CartService {
    @Autowired
    private final CartRepository cartRepository;

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

}
