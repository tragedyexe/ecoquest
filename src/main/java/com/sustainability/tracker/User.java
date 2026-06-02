package com.sustainability.tracker;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    private Long telegramId;
    private String username;
    private Integer points = 0;

    public User() {
    }

    public User(Long telegramId, String username, Integer points) {
        this.telegramId = telegramId;
        this.username = username;
        this.points = points;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public String getUsername1() {
        return username;
    }

    public Integer getPoints() {
        return points;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}