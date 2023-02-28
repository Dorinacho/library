package com.library.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.controllers.BookController;
import com.library.models.Book;
import com.library.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
//@WithMockUser(roles = {"ADMIN"})
@WithMockUser(authorities = {"ROLE_USER"})
class BookControllerTest {
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldGetBooks() throws Exception {
        List<Book> books = new ArrayList<>(
                Arrays.asList(new Book(1L, "978-1-7777-0182-3", "Martin Eden", "Jack London", 5, "Martin Eden is a sailor"),
                        new Book(2L, "978-1-8888-0182-3", "The spy", "Secret author", 8, "test book numero 2"),
                        new Book(3L, "978-1-9999-0182-3", "THe apps", "Dorin Suciu", 2, "test book numero tres")));

        when(bookRepository.findAll()).thenReturn(books);
        mockMvc.perform(get("/library/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(books.size()))
                .andDo(print());
    }

    @Test
    void shouldGetBookById() throws Exception {
        long id = 1L;
        Book book = new Book("978-5-6038-8700-5", "Martin Eden", "Jack London", 8,
                "Martin Eden was a sailor that wanted to seduce a girl from the upper circles of society.");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/library/books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$.availability").value(book.getAvailability()))
                .andExpect(jsonPath("$.description").value(book.getDescription()))
                .andDo(print());
    }

    @Test
    void shouldNotGetBookById() throws Exception {
        long id = 1L;

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/library/books/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldCreateBook() throws Exception {
        Book book = new Book("978-5-6038-8700-5", "Martin Eden", "Jack London", 8, "Martin Eden was a sailor that wanted to seduce a girl from the upper circles of society.");

        mockMvc.perform(post("/library/books").contentType(MediaType.APPLICATION_JSON).with(csrf())
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldUpdateBook() throws Exception {
        Long id = 2L;
        Book book = new Book("978-5-6038-8700-5", "Martin Eden", "Jack London", 8,
                "Martin Eden was a sailor that wanted to seduce a girl from the upper circles of society.");
        Book updatedBook = new Book("978-5-8888-8700-5", "MaRTin Eden", "Jack LONdon", 8,
                "This is a unit test");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/library/books/{id}", id).contentType(MediaType.APPLICATION_JSON).with(csrf())
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
                .andExpect(jsonPath("$.author").value(updatedBook.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(updatedBook.getIsbn()))
                .andExpect(jsonPath("$.availability").value(updatedBook.getAvailability()))
                .andExpect(jsonPath("$.description").value(updatedBook.getDescription()))
                .andDo(print());
    }

    @Test
    void shouldReturnNotFoundUpdateBook() throws Exception {
        Long id = 2L;
        Book updatedBook = new Book("978-5-8888-8700-5", "MaRTin Eden", "Jack LONdon", 8,
                "This is a unit test");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/library/books/{id}", id).contentType(MediaType.APPLICATION_JSON).with(csrf())
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldDeleteBook() throws Exception {
        long id = 1L;

        doNothing().when(bookRepository).deleteById(anyLong());

        mockMvc.perform(delete("/library/books/{id}", id).with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
