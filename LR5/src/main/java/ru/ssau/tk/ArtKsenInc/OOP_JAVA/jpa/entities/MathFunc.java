package ru.ssau.tk.ArtKsenInc.OOP_JAVA.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "MathFunc")
public class MathFunc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;

    @Column(nullable = false)
    private Long hash;

    public MathFunc() {
    }

    public MathFunc(Double x, Double y, Long hash) {
        this.x = x;
        this.y = y;
        this.hash = hash;
    }

}
