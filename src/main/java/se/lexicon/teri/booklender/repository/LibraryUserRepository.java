package se.lexicon.teri.booklender.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.teri.booklender.entity.LibraryUser;

import java.util.Optional;

public interface LibraryUserRepository extends CrudRepository<LibraryUser, Integer> {

    Optional<LibraryUser> findByEmailIgnoreCase(String email);
}
