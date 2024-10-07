package com.example.kitchensink.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kitchensink.model.Member;
import com.example.kitchensink.repository.MemberRespository;

@Service
public class MemberService {
    
    @Autowired
    private MemberRespository memberRepository;

    public Member findMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member findMemberByEmail(String email) {
        
        return memberRepository.findByEmail(email);
    }

    public List<Member> findAllMembersOrderedByName() {
        return memberRepository.findAllByOrderByNameAsc();
    }

    public Member saveMember(Member member) {
            return memberRepository.save(member);
    }
}
