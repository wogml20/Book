package com.book.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/book")
@Log4j2
@RequiredArgsConstructor
public class BookController {

    @GetMapping("/detail/{id}")
    public String bookDetail(@PathVariable String id) {
        return "item/itemDetail";
    }
}
