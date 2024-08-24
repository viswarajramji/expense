package com.demo.expense.kafka;


import com.demo.expense.api.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaEventMessage {
    private String eventType;
    private Event payload;
}