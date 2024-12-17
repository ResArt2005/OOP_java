package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "token"))
public class User {

    // Геттеры и сеттеры
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    // Конструкторы
    public User() {
        this.token = UUID.randomUUID().toString(); // Генерация уникального токена
        this.login = "Unknown";
        this.password = "Unknown";
    }

    public User(String login, String password) {
        this.token = UUID.randomUUID().toString(); // Генерация уникального токена
        this.login = login;
        this.password = password;
    }
}