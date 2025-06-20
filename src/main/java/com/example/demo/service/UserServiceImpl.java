package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserId;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    // 생성자 주입
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> findAllPaged(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findByNameOrEmailPaged(String name, String email, Pageable pageable) {
        return userRepository.findByNameContainingOrEmailContaining(name, email, pageable);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 사용자가 없습니다."));
    }


    @Override
    public boolean isNameDuplicate(String name) {
        return userRepository.existsByName(name);

    }

    @Override
    public void deleteUsersByIds(List<Long> ids)
    {
        userRepository.deleteAllById(ids);
    }

    @Override
    public Long getNextId() {
        return userRepository.getNextId(); // QueryDSL 사용 시 정의 필요
    }

}
