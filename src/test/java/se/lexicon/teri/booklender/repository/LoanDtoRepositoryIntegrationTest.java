package se.lexicon.teri.booklender.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.teri.booklender.entity.Book;
import se.lexicon.teri.booklender.entity.LibraryUser;
import se.lexicon.teri.booklender.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class LoanDtoRepositoryIntegrationTest {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LibraryUserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    private Loan loan;
    private LibraryUser testUser;
    private Book testBook;
    private long loanId;

    @BeforeEach
    void setUp() {
        testUser = new LibraryUser(LocalDate.now(), "Teri Sandstedt", "test@test.com");
        testBook = new Book("The Lord of the Rings", 30, new BigDecimal(15), "genre: fantasy");
        userRepository.save(testUser);
        bookRepository.save(testBook);

        loan = new Loan(testUser, testBook, LocalDate.now(), false);
        loanRepository.save(loan);
        loanId = loan.getId();
    }

    @AfterEach
    void tearDown() throws Exception {
        loanRepository.deleteAll();
    }

    @Test
    void test_findByID() {
        Optional<Loan> foundLoan = loanRepository.findById(loanId);
        assertEquals(loan, foundLoan.get());
    }

    @Test
    void test_findAll() {
        List<Loan> loans = (List<Loan>) loanRepository.findAll();
        int expectedSize = 1;
        assertEquals(expectedSize, loans.size());
    }

    @Test
    void test_delete() {
        loanRepository.delete(loan);

        List<Loan> loans = (List<Loan>) loanRepository.findAll();
        int expectedSize = 0;
        assertEquals(expectedSize, loans.size());
    }

    @Test
    void test_getBook() {
        assertEquals(testBook, loan.getBook());
    }

    @Test
    void test_getUser() {
        assertEquals(testUser, loan.getLibraryUser());
    }

    @Test
    void test_findByLibraryUserId() {
        List<Loan> loans = loanRepository.findByLibraryUserId(1);
        int expected = 1;
        assertEquals(expected, loans.size());
    }

    @Test
    void test_findByBookId() {
        List<Loan> loans = loanRepository.findByBookId(1);
        int expected = 1;
        assertEquals(expected, loans.size());
    }

    @Test
    void test_findByReturned() {
        List<Loan> loans = loanRepository.findByReturned(true);
        int expected = 0;
        assertEquals(expected, loans.size());
    }
}