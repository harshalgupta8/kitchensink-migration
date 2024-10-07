package com.example.kitchensink;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.kitchensink.model.Member;
import com.example.kitchensink.repository.MemberRespository;
import com.example.kitchensink.service.MemberService;


import java.util.Optional;

class MemberServiceTest {

    @Mock
    private MemberRespository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member mockMember;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMember = new Member();
        mockMember.setId(1L);
        mockMember.setName("John Doe");
        mockMember.setEmail("john.doe@mail.com");
        mockMember.setPhoneNumber("1234567890");
    }

    @Test
    void testFindMemberById_MemberExists() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(mockMember));

        Member foundMember = memberService.findMemberById(1L);

        assertNotNull(foundMember);
        assertEquals(mockMember.getId(), foundMember.getId());
        assertEquals(mockMember.getName(), foundMember.getName());
        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    void testFindMemberByEmail_MemberExists() {
        when(memberRepository.findByEmail("john.doe@mail.com")).thenReturn(mockMember);

        Member foundMember = memberService.findMemberByEmail("john.doe@mail.com");

        assertNotNull(foundMember);
        assertEquals(mockMember.getEmail(), foundMember.getEmail());
        verify(memberRepository, times(1)).findByEmail("john.doe@mail.com");
    }
}
