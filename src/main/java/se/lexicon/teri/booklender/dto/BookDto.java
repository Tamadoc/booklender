package se.lexicon.teri.booklender.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDto {

    private int id;
    private String title;
    private boolean available = true;
    private boolean reserved = false;
    private int maxLoanDays;
    private BigDecimal finePerDay;
    private String description;
}
