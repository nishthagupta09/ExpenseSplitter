package com.nishtha.ExpenseSplitter.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection="expenses")
public class Expenses {

    @Id
    private String Id;

    private String GroupId; //FOR GROUP CLASS
    private String createdBy; //userId of payer
    private double amount;
    private String type;
    private String category; //Food,Travel,Misc
    private String splitMethod; //EQUAL,PERCENTAGE,CUSTOM
    private LocalDateTime createdAt;
    private List<Split> splits;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Split {
        private String UserId;
        private double money;
        private String type;
        private double value;
    }


}
