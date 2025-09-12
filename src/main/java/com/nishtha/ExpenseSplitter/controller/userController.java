package com.nishtha.ExpenseSplitter.controller;

import com.nishtha.ExpenseSplitter.entity.User;
import com.nishtha.ExpenseSplitter.repository.userRepository;
import com.nishtha.ExpenseSplitter.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/split/user")
public class userController {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private userService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getUsers(){
        List<User> all=userService.getAllUsers();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveUser(user);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        String username=user.getUsername();
        User userInDB=userService.findByUsername(username);
        userInDB.setUsername(user.getUsername());
        userInDB.setPassword(user.getPassword());
        userService.saveUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByID(@RequestBody User user){
        String username=user.getUsername();
        userRepository.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
