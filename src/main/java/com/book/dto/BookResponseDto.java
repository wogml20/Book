package com.book.dto;


import lombok.Data;

@Data
public class BookResponseDto {
    private int display;
    private Item[] items;

    @Data
    static class Item {
        public String title;
        public String link;
        public String image;
        public String subtitle;
        public String pubDate;
        public String director;
        public String actor;
        public float userRating;
    }
}
