package se.lexicon.teri.booklender.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.teri.booklender.dto.BookDto;
import se.lexicon.teri.booklender.entity.Book;
import se.lexicon.teri.booklender.exception.DataNotFoundException;
import se.lexicon.teri.booklender.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    BookRepository repository;
    ModelMapper modelMapper;

    @Autowired
    public void setRepository(BookRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BookDto> findByReserved(boolean isReserved) {
        List<Book> bookList = new ArrayList<>();
        repository.findByReserved(isReserved).iterator().forEachRemaining(bookList::add);
        return bookList.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findByAvailable(boolean isAvailable) {
        List<Book> bookList = new ArrayList<>();
        repository.findByReserved(isAvailable).iterator().forEachRemaining(bookList::add);
        return bookList.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findByTitle(String title) {
        List<Book> bookList = new ArrayList<>();
        repository.findByTitleContainsIgnoreCase(title).iterator().forEachRemaining(bookList::add);
        return bookList.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDto findById(int id) throws DataNotFoundException {
        if (id == 0) {
            throw new IllegalArgumentException("Id should not be 0");
        }
        Optional<Book> optionalBook = repository.findById(id);
        if (optionalBook.isPresent()) {
            return modelMapper.map(optionalBook.get(), BookDto.class);
        } else {
            throw new DataNotFoundException("Dto not found");
        }
    }

    @Override
    public List<BookDto> findAll() {
        List<Book> bookList = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(bookList::add);
        return bookList.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookDto create(BookDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Dto should not be null");
        }
        if (dto.getId() != 0) {
            throw new IllegalArgumentException("Id should be 0");
        }
        Book bookEntity = modelMapper.map(dto, Book.class);
        Book savedEntity = repository.save(bookEntity);

        return modelMapper.map(savedEntity, BookDto.class);
    }

    @Transactional
    @Override
    public BookDto update(BookDto dto) throws DataNotFoundException {
        if (dto == null) {
            throw new IllegalArgumentException("Dto object should not be null");
        }
        if (dto.getId() == 0) {
            throw new IllegalArgumentException("Id should not be 0");
        }
        Optional<Book> optionalBook = repository.findById(dto.getId());
        if (optionalBook.isPresent()) {
            return modelMapper.map(repository.save(modelMapper.map(dto, Book.class)), BookDto.class);
        } else {
            throw new DataNotFoundException("Dto not found");
        }
    }

    @Transactional
    @Override
    public boolean deleteById(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("Id should not be 0");
        }
        Optional<Book> optionalBook = repository.findById(id);
        if (optionalBook.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
