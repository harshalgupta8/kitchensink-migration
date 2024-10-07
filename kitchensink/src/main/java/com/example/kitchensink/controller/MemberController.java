package com.example.kitchensink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kitchensink.service.MemberService;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

import com.example.kitchensink.model.Member;
import com.example.kitchensink.repository.MemberRespository;

@Validated
@RestController
@RequestMapping("/members")
public class MemberController {
    
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRespository memberRepository;

    //get all members
    @GetMapping()
    public ResponseEntity<List<Member>> getMembers() {
        List<Member> members = memberService.findAllMembersOrderedByName();
        if (members != null) {
            return ResponseEntity.ok(members);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Get a member by ID
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberService.findMemberById(id);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get a member by email
    @GetMapping("/email")
    public ResponseEntity<Member> getMemberByEmail(@RequestParam String email) {
        Member member = memberService.findMemberByEmail(email);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Add a new member
    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody @Valid Member member) {
        try {
            validateMember(member);
            Member createdMember = memberService.saveMember(member);
            return ResponseEntity.ok(createdMember);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    //validate member
    @PostMapping("/validate")
    public void validateMember(@RequestBody @Valid Member member) {
        // Check the uniqueness of the email address
        if (emailAlreadyExists(member.getEmail())) {
            throw new ValidationException("Unique Email Violation.");
        }
    }

    // check if the email already exists
    private boolean emailAlreadyExists(String email) {
        return memberRepository.findByEmail(email) != null;
    }
}
