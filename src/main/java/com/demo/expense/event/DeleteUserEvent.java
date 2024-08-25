package com.demo.expense.event;

import com.demo.expense.api.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserEvent implements Event {
    private Long userId;
}
