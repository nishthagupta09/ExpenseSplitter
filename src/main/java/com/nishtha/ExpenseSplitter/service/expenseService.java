package com.nishtha.ExpenseSplitter.service;

import com.nishtha.ExpenseSplitter.entity.Expenses;
import com.nishtha.ExpenseSplitter.entity.Group;
import com.nishtha.ExpenseSplitter.repository.expenseRepository;
import com.nishtha.ExpenseSplitter.repository.groupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class expenseService {

    @Autowired
    private groupRepository groupRepository;

    @Autowired
    private expenseRepository expenseRepository;

    public Expenses createExpense(Expenses expense){
        List<Expenses.Split> calculatedSplits;

        String splitType=expense.getSplits().isEmpty() ? "EQUAL" : expense.getSplits().get(0).getType().toUpperCase();

        switch(splitType){
            case "EQUAL":
                calculatedSplits=addEqualSplit(expense);
                break;
            case "PERCENTAGE":
                calculatedSplits=addPercentageSplit(expense);
                break;
            case "CUSTOM":
                calculatedSplits=addCustomSplit(expense);
                break;
            default:
                calculatedSplits=addEqualSplit(expense);
        }
        expense.setSplits(calculatedSplits);
        return expenseRepository.save(expense);
    }

    private List<Expenses.Split> addEqualSplit(Expenses expense) {
        expense.setCreatedAt(LocalDateTime.now());
        Group group = groupRepository.findById(expense.getGroupId()).orElseThrow(() -> new RuntimeException("Group Not Found"));

        List<String> members = group.getMemberIds();
        if (members.isEmpty()) {
            throw new RuntimeException("No Members found in the group");
        }

        double sharePerMember = expense.getAmount() / members.size();
        List<Expenses.Split> splits = new ArrayList<>();
        for (String memberId : members) {
            if (memberId.equals(expense.getCreatedBy())) {
                splits.add(Expenses.Split.builder()
                        .UserId(memberId)
                        .money(0.0)
                        .type("Equal Split")
                        .value(sharePerMember)
                        .build()
                );
            } else {
                splits.add(Expenses.Split.builder()
                        .UserId(memberId)
                        .money(sharePerMember)
                        .type("Equal Split")
                        .value(sharePerMember)
                        .build()
                );
            }
        }
        return splits;
    }

    private List<Expenses.Split> addPercentageSplit(Expenses expense) {
        double totalPercentage = expense.getSplits().stream().mapToDouble(Expenses.Split::getValue).sum();
        if (Math.abs(totalPercentage - 100.0) > 0.01) {
            throw new RuntimeException("Percentages must add up to 100");
        }

        List<Expenses.Split> result = new ArrayList<>();
        for (Expenses.Split s : expense.getSplits()) {
            double share = (s.getValue() / 100.0) * expense.getAmount();
            result.add(Expenses.Split.builder()
                    .UserId(s.getUserId())
                    .money(share)
                    .type("PERCENTAGE")
                    .value(s.getValue()) // store percentage
                    .build());
        }
        return result;
    }

    private List<Expenses.Split> addCustomSplit(Expenses expense) {
        double totalCustom = expense.getSplits().stream()
                .mapToDouble(Expenses.Split::getValue)
                .sum();

        if (Math.abs(totalCustom - expense.getAmount()) > 0.01) {
            throw new RuntimeException(
                    "Custom splits must sum to the total amount (" + expense.getAmount() + ")"
            );
        }

        List<Expenses.Split> result = new ArrayList<>();
        for (Expenses.Split s : expense.getSplits()) {
            result.add(Expenses.Split.builder()
                    .UserId(s.getUserId())
                    .type("CUSTOM")
                    .value(s.getValue())   // value = exact amount assigned
                    .money(s.getValue())  // here value == amount
                    .build());
        }
        return result;
    }


    public Map<String, Double> calculateBalances(String groupId) {
        List<Expenses> expenses = getExpenseByGroup(groupId);
        Map<String, Double> balances = new HashMap<>();
        for (Expenses expense : expenses) {
            String payer = expense.getCreatedBy();
            double totalAmount = expense.getAmount();

            balances.put(payer, balances.getOrDefault(payer, 0.0) + totalAmount);

            for (Expenses.Split split : expense.getSplits()) {
                balances.put(split.getUserId(), balances.getOrDefault(split.getUserId(), 0.0) - split.getMoney());
            }
        }

        return balances;
    }

    public List<Expenses> getExpenseByGroup(String groupId) {
        return expenseRepository.findByGroupId(groupId);
    }

    public Expenses updateExpense(String groupId, Expenses updatedExpense) {
        return expenseRepository.findById(groupId)
                .map(existing -> {
                    existing.setAmount(updatedExpense.getAmount());
                    existing.setCreatedBy(updatedExpense.getCreatedBy());
                    existing.setGroupId(updatedExpense.getGroupId());
                    existing.setSplits(updatedExpense.getSplits());

                    return createExpense(existing);
                })
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    public void deleteExpense(String groupId) {
        expenseRepository.deleteById(groupId);
    }


}


