package com.library.controllers;

import com.library.models.Author;
import com.library.repositories.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library/authors")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthorController {
    private final AuthorRepository authorRepository;

    Logger logger
            = LoggerFactory.getLogger(AuthorController.class);

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<List<Author>> getAuthors() {
        List<Author> authors;
        authors = authorRepository.findAll();
        if (!authors.isEmpty()) {
            return new ResponseEntity<>(authors, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<Author> getAuthorById(@PathVariable(name = "id") long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isPresent()) {
            return new ResponseEntity<>(author.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        try {
            authorRepository.save(author);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error => " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author authorData, @PathVariable(name = "id") Long authorID) {
        Optional<Author> author = authorRepository.findById(authorID);
        if (author.isPresent()) {
            Author _author = author.get();
            _author.setBiography(authorData.getBiography());
            _author.setBooks(authorData.getBooks());
            _author.setName(authorData.getName());
            _author.setBirthdate(authorData.getBirthdate());
            return new ResponseEntity<>(authorRepository.save(_author), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable(name = "id") Long authorID) {
        try {
            authorRepository.deleteById(authorID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error => " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
