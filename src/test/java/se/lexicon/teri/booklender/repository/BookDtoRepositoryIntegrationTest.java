package se.lexicon.teri.booklender.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.teri.booklender.entity.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class BookDtoRepositoryIntegrationTest {

    @Autowired
    private BookRepository repository;
    private Book book;
    private int bookId;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setTitle("The Lord of the Rings");
        book.setAvailable(true);
        book.setReserved(false);
        book.setMaxLoanDays(30);
        book.setFinePerDay(new BigDecimal(15));
        book.setDescription("genre: fantasy");

        repository.save(book);
        bookId = book.getId();
    }

    @AfterEach
    void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    void test_findByID() {
        Optional<Book> foundBook = repository.findById(bookId);
        assertEquals(book, foundBook.get());
    }

    @Test
    void test_findAll() {
        List<Book> books = (List<Book>) repository.findAll();
        int expectedSize = 1;
        assertEquals(expectedSize, books.size());
    }

    @Test
    void test_delete() {
        repository.delete(book);

        List<Book> books = (List<Book>) repository.findAll();
        int expectedSize = 0;
        assertEquals(expectedSize, books.size());
    }

    @Test
    void test_findByReserved() {
        List<Book> books = repository.findByReserved(false);
        int expectedSize = 1;
        assertEquals(expectedSize, books.size());
    }

    @Test
    void test_findByAvailable() {
        List<Book> books = repository.findByAvailable(true);
        int expectedSize = 1;
        assertEquals(expectedSize, books.size());
    }

    @Test
    void test_findByTitle() {
        List<Book> books = repository.findByTitleContainsIgnoreCase("lord of the rings");
        int expectedSize = 1;
        assertEquals(expectedSize, books.size());
    }
}