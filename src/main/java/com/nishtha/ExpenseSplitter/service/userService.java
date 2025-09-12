package com.nishtha.ExpenseSplitter.service;

import com.nishtha.ExpenseSplitter.entity.User;
import com.nishtha.ExpenseSplitter.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class userService {

    @Autowired
    private userRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findByID(String id){
        return userRepository.findById(id);
    }

    public void deleteByID(String id){
        userRepository.deleteById(id);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }


}
