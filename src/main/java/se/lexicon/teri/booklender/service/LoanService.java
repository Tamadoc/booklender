package se.lexicon.teri.booklender.service;

import se.lexicon.teri.booklender.dto.LoanDto;
import se.lexicon.teri.booklender.exception.DataNotFoundException;

import java.util.List;

public interface LoanService {

    LoanDto findById(long id) throws DataNotFoundException;

    List<LoanDto> findByBookId(int bookId);

    List<LoanDto> findByLibraryUserId(int userId);

    List<LoanDto> findByReturned(boolean isReturned);

    List<LoanDto> findAll();

    LoanDto create(LoanDto dto);

    LoanDto update(LoanDto dto) throws DataNotFoundException;

    boolean deleteById(long id);

}
