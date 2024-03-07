package com.example.enkai.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.enkai.common.DataNotFoundException;
import com.example.enkai.entity.User;
import com.example.enkai.repository.UserRepository;

@Repository
public class UserDao implements BaseDao<User> {
    @Autowired
    UserRepository repository;

    @Override
    public List<User> findAll() {
        // TODO 自動生成されたメソッド・スタブ
        return repository.findAll();
    }

    @Override
    public User findById(Integer id) throws DataNotFoundException {
        // TODO 自動生成されたメソッド・スタブ
        return this.repository.findById(id).orElseThrow(() -> new DataNotFoundException());
    }

    @Override
    public void save(User user) {
        // TODO 自動生成されたメソッド・スタブ
        this.repository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        // TODO 自動生成されたメソッド・スタブ
        try {
            User user = this.findById(id);
            this.repository.deleteById(user.getId());
        } catch (DataNotFoundException e) {
            System.out.println("no data");
        }
    }

    public User findByEmail(String email) throws DataNotFoundException {
        User user = this.repository.findByEmail(email);
        if(user == null) {
            throw new DataNotFoundException();
        }
        return user;
    }

}
