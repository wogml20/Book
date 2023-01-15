package com.book.controller;


import com.book.dto.BookSearchDto;
import com.book.dto.MainBookDto;
import com.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final BookService bookService;

    @GetMapping("/")
    public String main(BookSearchDto bookSearchDto, Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainBookDto> books = bookService.getMainBookPage(bookSearchDto, pageable);
        model.addAttribute("books", books);
        model.addAttribute("bookSearchDto", bookSearchDto);
        model.addAttribute("maxPage",5);

        return "main";
    }
}
