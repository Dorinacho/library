package com.library.repository;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    @Override
    List<Book> findAll();

    @Override
    <S extends Book> S save(S entity);

    @Override
    Optional<Book> findById(String s);

    @Override
    long count();

    @Override
    void deleteById(String s);
}
