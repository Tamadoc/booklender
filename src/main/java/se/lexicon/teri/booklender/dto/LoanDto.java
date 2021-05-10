package se.lexicon.teri.booklender.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanDto {

    private long id;
    private LibraryUserDto libraryUserDto;
    private BookDto bookDto;
    private LocalDate loanDate;
    private boolean returned = false;
}
