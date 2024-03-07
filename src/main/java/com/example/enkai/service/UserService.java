package com.example.enkai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.enkai.common.DataNotFoundException;
import com.example.enkai.common.PasswordHasher;
import com.example.enkai.dao.UserDao;
import com.example.enkai.entity.User;

@Service
public class UserService implements BaseService<User> {
    @Autowired
    private UserDao dao;

    @Override
    public List<User> findAll() {
        // TODO 自動生成されたメソッド・スタブ
        return dao.findAll();
    }

    @Override
    public User findById(Integer id) throws DataNotFoundException {
        // TODO 自動生成されたメソッド・スタブ
        return dao.findById(id);
    }

    @Override
    public User save(User user) {
        // TODO 自動生成されたメソッド・スタブ
        updateSecurityContext(user);
        dao.save(user);
        return user;
    }

    @Override
    public void deleteById(Integer id) {
        // TODO 自動生成されたメソッド・スタブ
        dao.deleteById(id);
    }

    public User findByEmail(String email) throws DataNotFoundException {
        return dao.findByEmail(email);
    }

    public User auth(User user) {
        try {
            User foundUser = dao.findByEmail(user.getEmail());
            if(PasswordHasher.matches(user.getPassword(),foundUser.getPassword())) {
                foundUser.setAuth(true);
                return foundUser;
            }
        } catch (DataNotFoundException e) {
        }
        user.setAuth(false);
        return user;
    }

    public boolean authenticate(String email, String password) throws DataNotFoundException {
        Optional<User> user = Optional.ofNullable(findByEmail(email));
        if (user.isPresent()) {
            // パスワードのチェックを行う（ここでは簡単のため平文で比較していますが、実際にはハッシュ化されたパスワードの比較を行うべきです）
            return user.get().getPassword().equals(password);
        }
        return false;
    }

    public boolean isUnique(String email) {
        try {
            dao.findByEmail(email);
            return false;
        } catch (DataNotFoundException e) {
            return true;
        }
    }
    /*
     * SpringSecurity側の更新
     */
    private void updateSecurityContext(User user) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("ADMIN")
                .build();
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        ));
    }

}

