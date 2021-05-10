package se.lexicon.teri.booklender.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.teri.booklender.dto.LoanDto;
import se.lexicon.teri.booklender.entity.Loan;
import se.lexicon.teri.booklender.exception.DataNotFoundException;
import se.lexicon.teri.booklender.repository.LoanRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    LoanRepository repository;
    ModelMapper modelMapper;

    @Autowired
    public void setRepository(LoanRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public LoanDto findById(long id) throws DataNotFoundException {
        if (id == 0) {
            throw new IllegalArgumentException("Id should not be 0");
        }
        Optional<Loan> optionalLoan = repository.findById(id);
        if (optionalLoan.isPresent()) {
            return modelMapper.map(optionalLoan.get(), LoanDto.class);
        } else {
            throw new DataNotFoundException("Dto not found");
        }
    }

    @Override
    public List<LoanDto> findByBookId(int bookId) {
        if (bookId == 0) {
            throw new IllegalArgumentException("Id should not be 0");
        }
        List<Loan> loanList = new ArrayList<>();
        repository.findByBookId(bookId).iterator().forEachRemaining(loanList::add);
        return loanList.stream()
                .map(loan -> modelMapper.map(loan, LoanDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanDto> findByLibraryUserId(int userId) {
        if (userId == 0) {
            throw new IllegalArgumentException("Id should not be 0");
        }
        List<Loan> loanList = new ArrayList<>();
        repository.findByLibraryUserId(userId).iterator().forEachRemaining(loanList::add);
        return loanList.stream()
                .map(loan -> modelMapper.map(loan, LoanDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanDto> findByReturned(boolean isReturned) {
        List<Loan> loanList = new ArrayList<>();
        repository.findByReturned(isReturned).iterator().forEachRemaining(loanList::add);
        return loanList.stream()
                .map(loan -> modelMapper.map(loan, LoanDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanDto> findAll() {
        List<Loan> loanList = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(loanList::add);
        return loanList.stream()
                .map(loan -> modelMapper.map(loan, LoanDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public LoanDto create(LoanDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Dto should not be null");
        }
        if (dto.getId() != 0) {
            throw new IllegalArgumentException("Id should be 0");
        }
        Loan loanEntity = modelMapper.map(dto, Loan.class);
        Loan savedEntity = repository.save(loanEntity);

        return modelMapper.map(savedEntity, LoanDto.class);
    }

    @Transactional
    @Override
    public LoanDto update(LoanDto dto) throws DataNotFoundException {
        if (dto == null) {
            throw new IllegalArgumentException("Dto object should not be null");
        }
        if (dto.getId() == 0) {
            throw new IllegalArgumentException("Id should not be 0");
        }
        Optional<Loan> optionalLoan = repository.findById(dto.getId());
        if (optionalLoan.isPresent()) {
            return modelMapper.map(repository.save(modelMapper.map(dto, Loan.class)), LoanDto.class);
        } else {
            throw new DataNotFoundException("Dto not found");
        }
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        if (id == 0) {
            throw new IllegalArgumentException("Id should not be 0");
        }
        Optional<Loan> optionalLoan = repository.findById(id);
        if (optionalLoan.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
