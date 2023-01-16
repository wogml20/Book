//package com.book.repository;
//
//import com.book.entity.Book;
//import com.book.entity.Item;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//import javax.transaction.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//@Transactional
//@TestPropertySource(locations = "classpath:application-test.properties")
//class ItemRepositoryTest {
//
//
//    @Autowired
//    ItemRepository itemRepository;
//
//    @Test
//    @DisplayName("책 재고 저장 테스트")
//    public void createBookTest() {
//        Item item = new Item();
//
//        item.setTitle("테스트 상품");
//        item.setprice(10000);
//        item.setStockNumber(100);
//        item.setDescription("테스트 상품 상세 설명");
//        Item savedBook = itemRepository.save(item);
//        System.out.println(savedBook.toString());
//    }
//
//}