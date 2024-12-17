package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "Logs")
public class Log {
    // Геттеры и сеттеры
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Timestamp timestamp;

    public Log() {
    }

    public Log(String message, Timestamp timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

}
