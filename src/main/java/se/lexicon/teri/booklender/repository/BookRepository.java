package se.lexicon.teri.booklender.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.teri.booklender.entity.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {

    List<Book> findByReserved(boolean isReserved);

    List<Book> findByAvailable(boolean isAvailable);

    List<Book> findByTitleContainsIgnoreCase(String title);

}
