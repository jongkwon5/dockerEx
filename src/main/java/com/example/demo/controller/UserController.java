package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private System system;

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id); // ✅ findById 메서드 호출
        model.addAttribute("user", user);
        return "userForm";
    }

    @PostMapping("/api")
    @ResponseBody
    public Map<String, Object> saveUserApi(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        if (userService.isNameDuplicate(user.getName())) {
            response.put("success", false);
            response.put("message", "이미 존재하는 이름입니다!");
        } else {
            user.setId(userService.getNextId()); // QueryDSL 활용 시
            userService.saveUser(user);
            response.put("success", true);
        }

        return response;
        }


    // 3️⃣ 사용자 목록
    @GetMapping
    public String listUsers(@RequestParam(required = false) String keyword,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(required = false) String sort,
                            Model model) {

        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);

        // 🔽 정렬 조건 확인

        if ("name".equals(sort)) {
            pageable = PageRequest.of(page, pageSize, Sort.by("name").ascending());
        } else if ("email".equals(sort)) {
            pageable = PageRequest.of(page, pageSize, Sort.by("email").ascending());
        } else if ("id".equals(sort)) {
            pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        } else {
            pageable = PageRequest.of(page, pageSize);
        }

        Page<User> userPage = (keyword == null || keyword.isEmpty())
                ? userService.findAllPaged(pageable)
                : userService.findByNameOrEmailPaged(keyword, keyword, pageable);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort); // 페이지 이동 시 정렬 유지

        return "userList";
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUsers(@RequestBody List<Long> selectedUserIds) {
        userService.deleteUsersByIds(selectedUserIds);

        return ResponseEntity.ok().build();
    }

}
