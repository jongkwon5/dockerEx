package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    boolean isNameDuplicate(String name);

    void saveUser(User user);

    void deleteUsersByIds(List<Long> selectedUserIds);

    Long getNextId();

    User findById(Long id);

    Page<User> findAllPaged(Pageable pageable);

    Page<User> findByNameOrEmailPaged(String name, String email, Pageable pageable);

}
