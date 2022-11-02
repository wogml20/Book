//package com.book.service;
//
//import com.book.constant.Role;
//import com.book.dto.MemberFormDto;
//import com.book.entity.Member;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.transaction.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//@Transactional
//
//class BookServiceTest {
//
//
//    public Member createMember() {
//        MemberFormDto memberFormDto = new MemberFormDto();
//        memberFormDto.setEmail("test@email.com");
//        memberFormDto.setName("홍길동");
//        memberFormDto.setPassword("1234");
//        memberFormDto.setRole(Role.USER);
//
//        return Member.createMember(memberFormDto, passwordEncoder);
//    }
//
//
//    @Test
//    @DisplayName("회원가입 테스트")
//    public void saveMemberTEst() {
//        Member member = createMember();
//        Member savedMember = memberService.saveMember(member);
//
//        assertEquals(member.getEmail(), savedMember.getEmail());
//        assertEquals(member.getName(), savedMember.getName());
//        assertEquals(member.getPassword(), savedMember.getPassword());
//        assertEquals(member.getRole(), savedMember.getRole());
//    }
//
//}