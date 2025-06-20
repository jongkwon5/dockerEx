package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long>, UserRepositoryCustom{
    boolean existsByName(String name);
    // 이름 또는 이메일이 keyword를 포함하는 사용자 검색
    Page<User> findByNameContainingOrEmailContaining(String name, String email, Pageable pageable);

}
