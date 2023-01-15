package com.book.controller;

import com.book.dto.BookDto;
import com.book.dto.CartDto;
import com.book.entity.Book;
import com.book.entity.Cart;
import com.book.entity.Member;
import com.book.repository.BookRepository;
import com.book.repository.CartRepository;
import com.book.service.BookService;
import com.book.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;

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
    BookDto bookDto;

    public ArrayList<BookDto> bookInfos = new ArrayList<>();
    ArrayList<BookDto> cartDtoList = new ArrayList<BookDto>();
    CartDto cartDto;

    Cart cart;
    @GetMapping(value = "/item/search")
    public String itemSearchGet() {
        return "item/itemForm";
    }

//    @PostMapping("/item/search")
//    public String itemSearchPost(@RequestParam(value="title") String title, Model model){
//        List<Book> bookDtoList = bookService.searchBooks(title);
//        model.addAttribute("bookDtoList", bookDtoList);
//        return "item/itemUserForm";
//    }

    @PostMapping("/item/search")
    public String searchBook(HttpServletRequest httpServletRequest, Model model) throws ParseException {

        bookInfos.clear();

        String query = httpServletRequest.getParameter("title");
        log.info("title_query ============= " + query);

        ByteBuffer buffer = StandardCharsets.UTF_8.encode(query);
        String encode = StandardCharsets.UTF_8.decode(buffer).toString();

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/book.json")
                .queryParam("query",encode)
                .queryParam("display",10)
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "zwvvcSwJFxCgtvbJTnvm")
                .header("X-Naver-Client-Secret","qqkxIC0KAZ")
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        String r = result.getBody();

        JSONObject value = new JSONObject(r);
        log.info("value = " + value);


        JSONArray items = value.getJSONArray("items");
        Integer total = (Integer) value.get("total");
        log.info("items = " + items);
        log.info("total = " + total);

//        for(int i = 0; i<items.length(); i++) {
//            bookInfos.add(new BookDto((String) items.getJSONObject(i).get("title"), (String) items.getJSONObject(i).get("link"),(String) items.getJSONObject(i).get("image"),(String) items.getJSONObject(i).get("author"),(String) items.getJSONObject(i).get("isbn"),Integer.parseInt((String) items.getJSONObject(i).get("discount")), (String) items.getJSONObject(i).get("publisher"),(String) items.getJSONObject(i).get("description")));
//        }
//
//        model.addAttribute("total",total);
//        model.addAttribute("bookInfos", bookInfos);


//        Book book = Book.createBook(bookInfos);
//        bookService.saveBook(book);

        return "item/itemForm";
    }



    @GetMapping("/item/cart")
    public String bookAddCartGet(Model model){
        model.addAttribute("bookDtos", bookRepository.findAll());
        return "item/itemCart";
    }

//    @PostMapping("/item/cart")
//    public String bookAddCartPost(HttpServletRequest httpServletRequest, Model model) throws Exception {
//
////        Integer stockNumber = Integer.valueOf(httpServletRequest.getParameter("stockNumber"));
//        String index = httpServletRequest.getParameter("index");
//        String title = httpServletRequest.getParameter("title");
////        String discount_t = httpServletRequest.getParameter("discount");
////        String imageSrc = httpServletRequest.getParameter("imageSrc");
//
////        log.info("수량 선택 ============= " + stockNumber);
//        log.info("선택한 id ============= " + index);
//        log.info("선택한 title ============= " + title);
////        log.info("선택한 discount ============= " + discount_t);
////        log.info("선택한 imageSrc ============= " + imageSrc);
//
//
//        Integer i = Integer.parseInt(index);
//
////        Integer discount = Integer.parseInt(discount_t);
////        Integer stockNumber = Integer.parseInt(query_stock_number);
//
//////
////        cartDtoList.add((new CartDto(title, imageSrc, discount, 1)));
//        cartDtoList.add(bookInfos.get(i));
////
//        try {
//            cart = Cart.cartAddBook(cartDtoList.get(i));
//            cartService.saveCart(cart);
//        } catch (IllegalStateException e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            return "item/itemCart";
//        }
////
//        log.info(cart);
//        log.info("====================bookrepository====================");
//        log.info(cartDtoList);
//
//////
//        model.addAttribute("cartDtos", cartDtoList);
//        return "item/itemCart";
//    }

    @PostMapping("/item/update")
    public String itemUpdate(HttpServletRequest httpServletRequest, Model model) throws Exception {

        Integer stockNumber = Integer.valueOf(httpServletRequest.getParameter("stockNumber"));
        log.info(httpServletRequest.getParameter("index"));
//        cartDtoList.get(index).setStockNumber(stockNumber);
//        cart = Cart.cartAddBook(cartDtoList.get(index));
//        cartService.saveCart(cart);

        model.addAttribute("cartDtos", cartDtoList);
        return "item/itemCart";
    }

    @GetMapping("/book/detail/{id}")
    public String bookDetail(@PathVariable("id") int id, Model model) {
        model.addAttribute("bookDetail", bookInfos.get(id));
        return "item/itemDetail";
    }


}
