package se.lexicon.teri.booklender.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.teri.booklender.entity.Loan;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {

    List<Loan> findByLibraryUserId(int userId);

    List<Loan> findByBookId(int bookId);

    List<Loan> findByReturned(boolean isReturned);
}
