package com.example.demo.Member;

import java.util.List;

public interface MemberRepository {
    List<Member> findAll();
    Member findById(int id);
    List<Member> findByName(String name);
    void save(Member member);
    void update(int id, Member updatedMember);
    void delete(int id);
}
