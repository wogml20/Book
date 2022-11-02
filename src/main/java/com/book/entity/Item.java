//package com.book.entity;
//
//import com.book.dto.AdminItemAddDto;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import javax.persistence.*;
//
//@Getter
//@Setter
//@ToString
//@Entity
//@Table(name = "item")
//public class Item {
//
//    @Id
//    @Column(name = "item_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(nullable = false, length = 50)
//    private String title;           //제목
//
//    @Lob
//    @Column(name = "img")
//    private String imageSrc;        //첵 표지
//
//
//    private String author;          //작가
//
//
//    private String isbn;            //isbn
//
//    @Column(name = "price", nullable = false)
//    private int discount;        //가격
//
//
//    @Column(nullable = false)
//    private int stockNumber;        //재고
//
//    private String publisher;       // 출판사
//
//    @Lob
//    @Column(nullable = false)
//    private String description;     //상세 설명
//
//
//    public static Item createItem (AdminItemAddDto adminItemAddDto) {
//        Item item = new Item();
//
//        item.setTitle(AdminItemAddDto);
//        item.setAuthor(AdminItemAddDto.getAuthor());
//        item.setStockNumber(AdminItemAddDto.getStockNumber());
//        item.setImageSrc(AdminItemAddDto.getImageSrc());
//        item.setIsbn(AdminItemAddDto.getIsbn());
//        item.setDiscount(AdminItemAddDto.getDiscount());
//        item.setDescription(AdminItemAddDto.getDescription());
//        item.setPublisher(AdminItemAddDto.getPublisher());
//
//        return item;
//    }
//}
