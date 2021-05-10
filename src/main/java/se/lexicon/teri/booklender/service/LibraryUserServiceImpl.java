package se.lexicon.teri.booklender.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.teri.booklender.dto.LibraryUserDto;
import se.lexicon.teri.booklender.entity.LibraryUser;
import se.lexicon.teri.booklender.exception.DataNotFoundException;
import se.lexicon.teri.booklender.repository.LibraryUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryUserServiceImpl implements LibraryUserService {

    LibraryUserRepository repository;
    ModelMapper modelMapper;

    @Autowired
    public void setRepository(LibraryUserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public LibraryUserDto findById(int id) throws DataNotFoundException {
        if (id == 0) {
            throw new IllegalArgumentException("Id should not be 0");
        }
        Optional<LibraryUser> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            return modelMapper.map(optionalUser.get(), LibraryUserDto.class);
        } else {
            throw new DataNotFoundException("Dto not found");
        }
    }

    @Override
    public LibraryUserDto findByEmail(String email) throws DataNotFoundException {
        if (email == null) {
            throw new IllegalArgumentException("Email should not be null");
        }
        Optional<LibraryUser> optionalUser = repository.findByEmailIgnoreCase(email);
        if (optionalUser.isPresent()) {
            return modelMapper.map(optionalUser.get(), LibraryUserDto.class);
        } else {
            throw new DataNotFoundException("Dto not found");
        }
    }

    @Override
    public List<LibraryUserDto> findAll() {
        List<LibraryUser> libraryUserList = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(libraryUserList::add);
        return libraryUserList.stream()
                .map(user -> modelMapper.map(user, LibraryUserDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public LibraryUserDto create(LibraryUserDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Dto should not be null");
        }
        if (dto.getId() != 0) {
            throw new IllegalArgumentException("Id should be 0");
        }
        LibraryUser userEntity = modelMapper.map(dto, LibraryUser.class);
        LibraryUser savedEntity = repository.save(userEntity);

        return modelMapper.map(savedEntity, LibraryUserDto.class);
    }

    @Transactional
    @Override
    public LibraryUserDto update(LibraryUserDto dto) throws DataNotFoundException {
        if (dto == null) {
            throw new IllegalArgumentException("Dto should not be null");
        }
        if (dto.getId() == 0) {
            throw new IllegalArgumentException("Id should not be 0");
        }
        Optional<LibraryUser> optionalUser = repository.findById(dto.getId());
        if (optionalUser.isPresent()) {
            return modelMapper.map(repository.save(modelMapper.map(dto, LibraryUser.class)), LibraryUserDto.class);
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
        Optional<LibraryUser> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
