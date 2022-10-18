package com.book.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Controller
@RequestMapping("/book")
public class Searchcontroller {

//    @RequestParam String query,
    @GetMapping("/search")
    public String searchBook(@RequestParam String query,Model model) throws ParseException {

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

        JSONParser parser = new JSONParser();
        JSONArray values = (JSONArray)parser.parse(r);
        JSONObject value = (JSONObject)values.get(0);
        System.out.println((String)value.get("lastBuildDate"));

        model.addAttribute("result", r);
        return "api/searchBook";
    }
}
