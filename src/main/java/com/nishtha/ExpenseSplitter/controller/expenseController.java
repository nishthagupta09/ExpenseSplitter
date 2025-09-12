package com.nishtha.ExpenseSplitter.controller;

import com.nishtha.ExpenseSplitter.entity.Expenses;
import com.nishtha.ExpenseSplitter.repository.expenseRepository;
import com.nishtha.ExpenseSplitter.service.expenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/split/expenses")
public class expenseController {

    @Autowired
    private expenseService expenseService;

    @Autowired
    private expenseRepository expenseRepository;

}
