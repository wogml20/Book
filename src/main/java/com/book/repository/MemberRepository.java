package com.book.repository;

import com.book.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    Member findByUserid(String userid);

    Member findByNameAndEmail(String name, String email);

    Member findByUseridAndEmail(String userId, String email);
}
