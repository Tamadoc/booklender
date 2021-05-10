package se.lexicon.teri.booklender.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class LibraryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private LocalDate regDate;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;


    public LibraryUser(LocalDate regDate, String name, String email) {
        this.regDate = regDate;
        this.name = name;
        this.email = email;
    }
}
