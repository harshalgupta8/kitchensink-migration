package com.example.kitchensink;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.kitchensink.controller.MemberController;
import com.example.kitchensink.model.Member;
import com.example.kitchensink.service.MemberService;

import java.util.ArrayList;
import java.util.List;

public class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    private Member mockMember;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMember = new Member();
        mockMember.setId(1L);
        mockMember.setName("John Smith");
        mockMember.setEmail("john.smith@mailinator.com");
        mockMember.setPhoneNumber("2125551212");
    }

    @Test
    void testGetMembers() {
        List<Member> members = new ArrayList<>();
        members.add(mockMember);

        when(memberService.findAllMembersOrderedByName()).thenReturn(members);

        ResponseEntity<List<Member>> response = memberController.getMembers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("John Smith", response.getBody().get(0).getName());
        verify(memberService, times(1)).findAllMembersOrderedByName();
    }

    @Test
    void testGetMemberById_Found() {
        when(memberService.findMemberById(1L)).thenReturn(mockMember);

        ResponseEntity<Member> response = memberController.getMemberById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("John Smith", response.getBody().getName());
        verify(memberService, times(1)).findMemberById(1L);
    }

    @Test
    void testGetMemberById_NotFound() {
        when(memberService.findMemberById(1L)).thenReturn(null);

        ResponseEntity<Member> response = memberController.getMemberById(1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(memberService, times(1)).findMemberById(1L);
    }

    @Test
    void testGetMemberByEmail_Found() {
        when(memberService.findMemberByEmail(mockMember.getEmail())).thenReturn(mockMember);

        ResponseEntity<Member> response = memberController.getMemberByEmail(mockMember.getEmail());

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("John Smith", response.getBody().getName());
        verify(memberService, times(1)).findMemberByEmail(mockMember.getEmail());
    }

    @Test
    void testGetMemberByEmail_NotFound() {
        when(memberService.findMemberByEmail(mockMember.getEmail())).thenReturn(null);

        ResponseEntity<Member> response = memberController.getMemberByEmail(mockMember.getEmail());

        assertEquals(404, response.getStatusCodeValue());
        verify(memberService, times(1)).findMemberByEmail(mockMember.getEmail());
    }

    
}

