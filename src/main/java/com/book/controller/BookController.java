package com.book.controller;

import com.book.dto.BookFormDto;
import com.book.service.BookService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
