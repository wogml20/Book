package com.book.controller;

import com.book.dto.BookFormDto;
import com.book.dto.CartBookDto;
import com.book.entity.Book;
import com.book.service.BookService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
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

    @GetMapping("/search")
    public String bookSearch(@RequestParam String title, Model model) {

        log.info("========================================");
        log.info(title);

        List<BookFormDto> bookFormDto = bookService.searchBooks(title);


        model.addAttribute("total", bookFormDto.size());
        model.addAttribute("bookInfos", bookFormDto);
        return "item/itemForm";
    }
}