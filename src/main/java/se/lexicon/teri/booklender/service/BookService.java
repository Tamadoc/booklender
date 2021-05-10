package se.lexicon.teri.booklender.service;

import se.lexicon.teri.booklender.dto.BookDto;
import se.lexicon.teri.booklender.exception.DataNotFoundException;

import java.util.List;

public interface BookService {

    List<BookDto> findByReserved(boolean isReserved);

    List<BookDto> findByAvailable(boolean isAvailable);

    List<BookDto> findByTitle(String title);

    BookDto findById(int id) throws DataNotFoundException;

    List<BookDto> findAll();

    BookDto create(BookDto dto);

    BookDto update(BookDto dto) throws DataNotFoundException;

    boolean deleteById(int id);

}
