package com.sustainability.tracker;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "actions")
public class Action {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private Long telegramId;
    private String actionName;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Integer points;
}
