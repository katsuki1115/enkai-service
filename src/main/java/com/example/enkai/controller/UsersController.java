package com.example.enkai.controller;

import com.example.enkai.common.DataNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.enkai.common.FlashData;
import com.example.enkai.entity.User;
import com.example.enkai.service.UserService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UserService userService;

    /*
     * 新規登録
     */
    @PostMapping("/create")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body("エラーメッセージ");
        }
        if (!userService.isUnique(user.getEmail())) {
            // バリデーションエラーがある場合、適切なレスポンスを返す
            return ResponseEntity.badRequest().body("メールアドレスが重複しています");
        }
        user.encodePassword(user.getPassword());
        User registUser = userService.save(user);
        user.setAuth(true);
        return new ResponseEntity<>(registUser, HttpStatus.CREATED);
    }
    /*
     * ログイン認証
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody User user, BindingResult result) throws DataNotFoundException {
        boolean isAuthenticated = userService.authenticate(user.getEmail(), user.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok().body("認証成功");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("認証失敗");
        }
    }
}
