package com.book.service;


import com.book.dto.CartBookDto;
import com.book.dto.CartDetailDto;
import com.book.dto.CartOrderDto;
import com.book.dto.OrderDto;
import com.book.entity.Book;
import com.book.entity.Cart;
import com.book.entity.CartBook;
import com.book.entity.Member;
import com.book.repository.BookRepository;
import com.book.repository.CartBookRepository;
import com.book.repository.CartRepository;
import com.book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartBookRepository cartBookRepository;

    private final OrderService orderService;

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Long addCart(CartBookDto cartBookDto, String userid) {
        Book book = bookRepository.findById(cartBookDto.getBookId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByUserid(userid);

        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        CartBook savedCartBook = cartBookRepository.findByCartIdAndBookId(cart.getId(), book.getId());

        if(savedCartBook != null) {
            savedCartBook.addCount(cartBookDto.getStockNumber());
            return savedCartBook.getId();
        }else {
            CartBook cartBook = CartBook.createCartBook(cart, book, cartBookDto.getStockNumber());
            cartBookRepository.save(cartBook);
            return cartBook.getId();
        }
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String userid) {
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByUserid(userid);
        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null) {
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartBookRepository.findCartDetailDtoList(cart.getId());

        return cartDetailDtoList;
    }

    @Transactional(readOnly = true)
    public boolean validateCartBook(Long cartBookId, String userid) {
        Member curMember = memberRepository.findByUserid(userid);
        CartBook cartBook = cartBookRepository.findById(cartBookId)
                .orElseThrow(EntityNotFoundException::new);

        Member savedMember = cartBook.getCart().getMember();

        if(!StringUtils.equals(curMember.getUserid(), savedMember.getUserid())) {
            return false;
        }

        return true;
    }

    public void updateCartBookCount(Long cartBookId, int count) {
        CartBook cartBook = cartBookRepository.findById(cartBookId)
                .orElseThrow(EntityNotFoundException::new);

        cartBook.updateCount(count);
    }

    public void deleteCartBook(Long cartBookId) {
        CartBook cartBook = cartBookRepository.findById(cartBookId)
                .orElseThrow(EntityNotFoundException::new);
        cartBookRepository.delete(cartBook);
    }

    public Long orderCartBook(List<CartOrderDto> cartOrderDtoList, String userid){
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartBook cartBook = cartBookRepository
                    .findById(cartOrderDto.getCartBookId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setBookId(cartBook.getBook().getId());
            orderDto.setStockNumber(cartBook.getStockNumber());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, userid);
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartBook cartBook = cartBookRepository
                    .findById(cartOrderDto.getCartBookId())
                    .orElseThrow(EntityNotFoundException::new);
            cartBookRepository.delete(cartBook);
        }

        return orderId;
    }
}
