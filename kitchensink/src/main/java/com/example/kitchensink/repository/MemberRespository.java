package com.example.kitchensink.repository;

import com.example.kitchensink.model.Member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRespository extends JpaRepository<Member,Long>{
    
    Optional<Member> findById(Long id);

    Member findByEmail(String email);

    List<Member> findAllByOrderByNameAsc();
}
