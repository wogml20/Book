package com.book.controller;

import com.book.dto.BookDto;
import com.book.dto.BookFormDto;
import com.book.entity.Book;
import com.book.repository.BookRepository;
import com.book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;


@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;

    @Autowired
    BookRepository bookRepository;

    ArrayList<BookDto> bookInfos = new ArrayList<>();

    @GetMapping(value = "/item/new")
    public String itemForm(Model model) {
        model.addAttribute("bookFormDto", new BookFormDto());
        return "item/adminItemForm";
    }

    @PostMapping("/item/new")
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
        log.info(value);

        JSONArray items = value.getJSONArray("items");
        Integer total = (Integer) value.get("total");
        log.info(items);

        for(int i = 0; i<items.length(); i++) {
            bookInfos.add(new BookDto((String) items.getJSONObject(i).get("title"),(String) items.getJSONObject(i).get("image"),(String) items.getJSONObject(i).get("author"), (String) items.getJSONObject(i).get("isbn"), Integer.parseInt((String) items.getJSONObject(i).get("discount")), (String) items.getJSONObject(i).get("publisher"),(String) items.getJSONObject(i).get("description")));

        }

        model.addAttribute("total", total);
        model.addAttribute("bookInfos", bookInfos);

        return "item/adminItemForm";
    }

    @GetMapping("/item/add/{id}")
    public String bookAddPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("bookDetail", bookInfos.get(id));
        model.addAttribute("bookFormDto", new BookFormDto());
        return "item/itemAdd";
    }

    @PostMapping("/item/add")
    public String bookAdd(@Valid BookFormDto bookFormDto, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest) throws Exception {

        Integer stockNumber = Integer.valueOf(httpServletRequest.getParameter("stockNumber"));
        Integer discount = Integer.valueOf(httpServletRequest.getParameter("discount"));
        String title = httpServletRequest.getParameter("title");
        String author = httpServletRequest.getParameter("author");
        String publisher = httpServletRequest.getParameter("publisher");
        String isbn = httpServletRequest.getParameter("isbn");
        String imgSrc = httpServletRequest.getParameter("imgSrc");

        log.info(title);
        log.info(stockNumber);
        log.info(discount);


        if(bindingResult.hasErrors()) {
            log.info("오류 발생");
            return "/item/itemAdd";
        }

//        if(bookFormDto.getId() == null) {
//            log.info("오류 발생");
//            return "/item/itemAdd";
//        }

        try {
            bookFormDto.setTitle(title);
            bookFormDto.setImageSrc(imgSrc);
            bookFormDto.setAuthor(author);
            bookFormDto.setIsbn(isbn);
            bookFormDto.setPublisher(publisher);
            bookFormDto.setStockNumber(stockNumber);
            bookFormDto.setDiscount(discount);

            bookService.saveBook(bookFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "item/adminItemForm";
        }

        return "redirect:/";
    }


//    @GetMapping("/item/add")
//    public String bootManage(Model model) throws Exception {
//        model.addAttribute("bookDtos", bookRepository.findAll());
//        return "item/itemAdd";
//    }
}
