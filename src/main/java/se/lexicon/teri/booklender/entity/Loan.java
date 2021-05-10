package se.lexicon.teri.booklender.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "library_user_id")
    private LibraryUser libraryUser;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private LocalDate loanDate;

    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean returned = false;


    public Loan(LibraryUser libraryUser, Book book, LocalDate loanDate, boolean returned) {
        this.libraryUser = libraryUser;
        this.book = book;
        this.loanDate = loanDate;
        this.returned = returned;
    }

    public boolean isOverdue() {
        if (!returned) {
            LocalDate returnByDate = loanDate.plusDays(book.getMaxLoanDays());

            if (LocalDate.now().isAfter(returnByDate)) {
                return true;
            }
        }
        return false;
    }

    public BigDecimal getFine() {
        if (isOverdue()) {
            LocalDate returnByDate = loanDate.plusDays(book.getMaxLoanDays());
            int daysOverdue = Period.between(returnByDate, LocalDate.now()).getDays();

            return new BigDecimal(daysOverdue * book.getFinePerDay().floatValue());
        }
        return new BigDecimal(0);
    }

    public boolean extendLoan(int days) {
        book.setMaxLoanDays(book.getMaxLoanDays() + days);
        return true;
    }
}
