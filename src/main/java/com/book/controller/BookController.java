package com.book.controller;

import com.book.dto.BookFormDto;
import com.book.entity.Book;
import com.book.service.BookService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/detail/{bookId}")
    public String bookDetail(@PathVariable Long bookId, Model model) {
        BookFormDto bookFormDto = bookService.getBookDtl(bookId);
        model.addAttribute("bookDetail", bookFormDto);
        return "item/itemDetail";
    }

    @PostMapping("/search")
    public String bookSearch(@RequestParam("title") String title, Model model) {
        BookFormDto bookFormDto = bookService.searchBooks(title);
        log.info("===================title====================");
        log.info(title);
        log.info(bookFormDto);
        return "";
    }
//        model.addAttribute("bookInfos", bookFormDto);

//        log.info(bookList.size());
//        return "";
//        return "item/itemForm";
}
