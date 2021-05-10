package se.lexicon.teri.booklender.service;

import se.lexicon.teri.booklender.dto.LibraryUserDto;
import se.lexicon.teri.booklender.exception.DataNotFoundException;

import java.util.List;

public interface LibraryUserService {

    LibraryUserDto findById(int id) throws DataNotFoundException;

    LibraryUserDto findByEmail(String email) throws DataNotFoundException;

    List<LibraryUserDto> findAll();

    LibraryUserDto create(LibraryUserDto dto);

    LibraryUserDto update(LibraryUserDto dto) throws DataNotFoundException;

    boolean deleteById(int id);
}
