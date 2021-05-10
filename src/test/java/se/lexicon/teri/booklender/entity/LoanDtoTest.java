package se.lexicon.teri.booklender.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoanDtoTest {

    private Loan testLoan;

    @BeforeEach
    void setUp() {
        LibraryUser testUser = new LibraryUser();
        testUser.setName("Teri Sandstedt");
        testUser.setEmail("test@test.com");
        Book testBook = new Book("The Lord of the Rings", 30, new BigDecimal(15), "genre: fantasy");

        testLoan = new Loan(testUser, testBook, LocalDate.of(2021, 4, 1), false);
    }

    @Test
    void test_isOverdue() {
        assertTrue(testLoan.isOverdue());
    }

    @Test
    void test_getFine() {
        assertEquals(new BigDecimal(30), testLoan.getFine());
    }

    @Test
    void test_extendLoan() {
        testLoan.extendLoan(30);
        assertFalse(testLoan.isOverdue());
    }
}