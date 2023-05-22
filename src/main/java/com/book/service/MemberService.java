package com.book.service;


import com.book.entity.Member;
import com.book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByUserid(member.getUserid());
        if(findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserid(userid);

        if(member == null) {
            throw new UsernameNotFoundException(userid);
        }

        return User.builder()
                .username(member.getUserid())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public Member findMember(String name, String email) {
        Member findMember = memberRepository.findByNameAndEmail(name, email);
        return findMember;
    }

    public Member getPwdSearch(String userid, String email) {
        Member findMember = memberRepository.findByUseridAndEmail(userid, email);
        return findMember;
    }
    public void setPwdChange(String userid, String pwd){
        Member member = memberRepository.findByUserid(userid);
        if (member != null) {
            member.setPassword(pwd);
            memberRepository.save(member);
            log.info("비밀번호가 성공적으로 변경되었습니다.");
        } else {
            log.info("사용자를 찾을 수 없습니다.");
        }
    }
}
