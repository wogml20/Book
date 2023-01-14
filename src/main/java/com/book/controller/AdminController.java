//package com.book.controller;
//
//import com.book.dto.BookDto;
//import com.book.entity.Book;
//import com.book.repository.BookRepository;
//import com.book.service.BookService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//import javax.servlet.http.HttpServletRequest;
//import java.net.URI;
//import java.nio.ByteBuffer;
//import java.nio.charset.StandardCharsets;
//import java.text.ParseException;
//import java.util.ArrayList;
//
//
//@Controller
//@Log4j2
//@RequiredArgsConstructor
//@RequestMapping("/admin")
//public class AdminController {
//
//    @Autowired
//    BookService bookService;
//
//    @Autowired
//    BookRepository bookRepository;
//
//    ArrayList<BookDto> bookInfos = new ArrayList<>();
//
//    @GetMapping(value = "/item/new")
//    public String itemForm() {
//        return "item/itemForm";
//    }
//
//    @PostMapping("/item/new")
//    public String searchBook(HttpServletRequest httpServletRequest, Model model) throws ParseException {
//
//        bookInfos.clear();
//        String query = httpServletRequest.getParameter("title");
//        log.info("title_query ============= " + query);
//
//        ByteBuffer buffer = StandardCharsets.UTF_8.encode(query);
//        String encode = StandardCharsets.UTF_8.decode(buffer).toString();
//
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://openapi.naver.com")
//                .path("/v1/search/book.json")
//                .queryParam("query",encode)
//                .queryParam("display",10)
//                .encode()
//                .build()
//                .toUri();
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        RequestEntity<Void> req = RequestEntity
//                .get(uri)
//                .header("X-Naver-Client-Id", "zwvvcSwJFxCgtvbJTnvm")
//                .header("X-Naver-Client-Secret","qqkxIC0KAZ")
//                .build();
//
//        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
//        String r = result.getBody();
//
//        JSONObject value = new JSONObject(r);
//        log.info(value);
//
//        JSONArray items = value.getJSONArray("items");
//        log.info(items);
//
//        for(int i = 0; i<items.length(); i++) {
//            bookInfos.add(new BookDto((String) items.getJSONObject(i).get("title"), (String) items.getJSONObject(i).get("link"),(String) items.getJSONObject(i).get("image"),(String) items.getJSONObject(i).get("author"),(String) items.getJSONObject(i).get("isbn"), (Integer) items.getJSONObject(i).get("discount"),Integer.parseInt(String.valueOf(100)), (String) items.getJSONObject(i).get("publisher"),(String) items.getJSONObject(i).get("description")));
//        }
//
//        model.addAttribute("bookInfos", bookInfos);
//
//        return "item/itemForm";
//    }
//
//    @PostMapping("/item/add")
//    public String bookAdd(HttpServletRequest httpServletRequest, Model model) throws Exception {
//
//        String query_stock_number = httpServletRequest.getParameter("stockNumber");
//        String query_id = httpServletRequest.getParameter("index");
//
//        log.info("수량 선택 ============= " + query_stock_number);
//        log.info("선택한 id ============= " + query_id);
//
//        Integer value = Integer.parseInt(query_id);
//        Integer stockNumber = Integer.parseInt(query_stock_number);
//
//        Book book;
//
//
//        try {
//            book = Book.createBook(stockNumber, bookInfos.get(value));
//            bookService.saveBook(book);
//        } catch (IllegalStateException e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            return "item/itemAdd";
//        }
//
//        log.info(book);
//        log.info("====================bookrepository====================");
//        log.info(bookRepository.findAll());
//
//
//        model.addAttribute("bookDtos", bookRepository.findAll());
//        return "item/itemAdd";
//    }
//
//
//    @GetMapping("/item/add")
//    public String bootManage(Model model) throws Exception {
//        model.addAttribute("bookDtos", bookRepository.findAll());
//        return "item/itemAdd";
//    }
//}
