package com.demo.expense.kafka;

import com.demo.expense.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaCommandMessage {
    private String commandType;
    private Command payload;
}