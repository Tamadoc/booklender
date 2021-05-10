package se.lexicon.teri.booklender.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LibraryUserDto {

    private int id;
    private LocalDate regDate;
    private String name;
    private String email;
}
