package com.nishtha.ExpenseSplitter.repository;

import com.nishtha.ExpenseSplitter.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepository extends MongoRepository<User,String> {
    User findByUsername(String username);

    void deleteByUsername(String username);
}
