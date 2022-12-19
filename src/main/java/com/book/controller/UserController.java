package com.book.controller;

import com.book.dto.CartDto;
import com.book.entity.Book;
import com.book.entity.Cart;
import com.book.repository.BookRepository;
import com.book.repository.CartRepository;
import com.book.service.BookService;
import com.book.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookService bookService;

    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepository;

    ArrayList<CartDto> cartDtoList = new ArrayList<CartDto>();
    CartDto cartDto;

    @GetMapping(value = "/item/search")
    public String itemSearchGet() {
        return "item/itemUserForm";
    }

    @PostMapping("/item/search")
    public String itemSearchPost(@RequestParam(value="title") String title, Model model){
        List<Book> bookDtoList = bookService.searchBooks(title);
        model.addAttribute("bookDtoList", bookDtoList);
        return "item/itemUserForm";
    }


    @GetMapping("/item/cart")
    public String bookAddCartGet(Model model){
        model.addAttribute("bookDtos", bookRepository.findAll());
        return "item/itemCart";
    }

    @PostMapping("/item/cart")
    public String bookAddCartPost(HttpServletRequest httpServletRequest, Model model) throws Exception {

        Integer stockNumber = Integer.valueOf(httpServletRequest.getParameter("stockNumber"));
        String index = httpServletRequest.getParameter("index");
        String title = httpServletRequest.getParameter("title");
        String discount_t = httpServletRequest.getParameter("discount");
        String imageSrc = httpServletRequest.getParameter("imageSrc");

        log.info("수량 선택 ============= " + stockNumber);
        log.info("선택한 id ============= " + index);
        log.info("선택한 title ============= " + title);
        log.info("선택한 discount ============= " + discount_t);
        log.info("선택한 imageSrc ============= " + imageSrc);


        Integer i = Integer.parseInt(index);
        Integer discount = Integer.parseInt(discount_t);
//        Integer stockNumber = Integer.parseInt(query_stock_number);
        Cart cart;
////
        cartDtoList.add((new CartDto(title, imageSrc, discount, stockNumber)));
//
        try {
            cart = Cart.cartAddBook(cartDtoList.get(i));
            cartService.saveCart(cart);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "item/itemCart";
        }
//
        log.info(cart);
        log.info("====================bookrepository====================");
        log.info(cartDtoList);

////
        model.addAttribute("cartDtos", cartDtoList);
        return "item/itemCart";
    }
}
