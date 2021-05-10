package se.lexicon.teri.booklender.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.teri.booklender.entity.LibraryUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class LibraryUserDtoRepositoryIntegrationTest {

    @Autowired
    private LibraryUserRepository repository;
    private LibraryUser libraryUser;
    private int userId;

    @BeforeEach
    void setUp() {
        libraryUser = new LibraryUser();
        libraryUser.setName("Teri Sandstedt");
        libraryUser.setRegDate(LocalDate.of(2010, 1, 1));
        libraryUser.setEmail("test@test.se");

        repository.save(libraryUser);
        userId = libraryUser.getId();
    }

    @AfterEach
    void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    void test_findByID() {
        Optional<LibraryUser> foundUser = repository.findById(userId);
        assertEquals(libraryUser, foundUser.get());
    }

    @Test
    void test_findAll() {
        List<LibraryUser> users = (List<LibraryUser>) repository.findAll();
        int expectedSize = 1;
        assertEquals(expectedSize, users.size());
    }

    @Test
    void test_delete() {
        repository.delete(libraryUser);

        List<LibraryUser> users = (List<LibraryUser>) repository.findAll();
        int expectedSize = 0;
        assertEquals(expectedSize, users.size());
    }

    @Test
    void test_findByEmail() {
        Optional<LibraryUser> foundUser = repository.findByEmailIgnoreCase("test@test.se");
        assertEquals(libraryUser, foundUser.get());
    }
}