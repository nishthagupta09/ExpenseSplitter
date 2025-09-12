package com.nishtha.ExpenseSplitter.repository;

import com.nishtha.ExpenseSplitter.entity.Expenses;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface expenseRepository extends MongoRepository<Expenses,String> {
    List<Expenses> findByGroupId(String GroupId);
}
