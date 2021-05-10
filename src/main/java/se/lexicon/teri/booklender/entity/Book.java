package se.lexicon.teri.booklender.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TINYINT(1) default 1")
    private boolean available = true;

    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean reserved = false;

    @Column(nullable = false)
    private int maxLoanDays;

    @Column(nullable = false)
    private BigDecimal finePerDay;

    @Column(nullable = false)
    private String description;


    public Book(String title, int maxLoanDays, BigDecimal finePerDay, String description) {
        this.title = title;
        this.maxLoanDays = maxLoanDays;
        this.finePerDay = finePerDay;
        this.description = description;
    }
}
