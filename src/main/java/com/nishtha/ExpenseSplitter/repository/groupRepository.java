package com.nishtha.ExpenseSplitter.repository;

import com.nishtha.ExpenseSplitter.entity.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface groupRepository extends MongoRepository<Group,String> {
}
