package com.library.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.controllers.LoanController;
import com.library.dto.LoanDTO;
import com.library.models.Book;
import com.library.models.Loan;
import com.library.models.User;
import com.library.service.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LoanController.class)
@WithMockUser(roles = {"ADMIN"})
class LoanControllerTest {
    @MockBean
    private LoanService loanService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldGetLoans() throws Exception {
        List<Loan> loans = new ArrayList<>(
                Arrays.asList(new Loan(new Book(), new User(), LocalDate.now()),
                        new Loan(new Book(), new User(), LocalDate.now()),
                        new Loan(new Book(), new User(), LocalDate.now())));

        when(loanService.getLoans()).thenReturn(loans);
        mockMvc.perform(get("/library/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(loans.size()))
                .andDo(print());

    }

    @Test
    void shouldGetLoansForUser() throws Exception {
        List<LoanDTO> loans = new ArrayList<>(
                Arrays.asList(new LoanDTO("Book 1", LocalDate.now(), LocalDate.now()),
                        new LoanDTO("Book 2", LocalDate.now(), LocalDate.now()),
                        new LoanDTO("Book 3", LocalDate.now(), LocalDate.now())));

        String username = "Test.user";
        when(loanService.getLoansForUser(anyString())).thenReturn(loans);
        mockMvc.perform(get("/library/loans/user/{username}", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(loans.size()))
                .andDo(print());

    }

//    @Test
//    void shouldGetLoanById() throws Exception {
//        long id = 1L;
//        Book book = new Book(id, "978-5-6038-8700-5", "Martin Eden", "Jack London", 8,
//                "Martin Eden was a sailor that wanted to seduce a girl from the upper circles of society.");
//        User user = new User("Marin Marcel", "marin@yahoo.com", "marin.marcel");
//        Loan loan = new Loan(book, user, LocalDate.now());
//        when(loanService.getLoanByID(anyLong())).thenReturn(Optional.of(loan));
//        mockMvc.perform(get("/library/loans/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.book").value(loan.getBook()))
//                .andExpect(jsonPath("$.user").value(loan.getUser()))
//                .andExpect(jsonPath("$.loanDate").value(loan.getLoanDate()))
//                .andDo(print());
//
//    }

    @Test
    void shouldAddLoan() throws Exception {
//        long id = 1L;
        Book book = new Book("978-5-6038-8700-5", "Martin Eden", "Jack London", 8,
                "Martin Eden was a sailor that wanted to seduce a girl from the upper circles of society.");
        User user = new User("Marin Marcel", "marin@yahoo.com", "marin.marcel");
        Loan loan = new Loan(book, user, LocalDate.now());
        when(loanService.addLoan(any(Loan.class))).thenReturn(loan);
        mockMvc.perform(post("/library/loans").contentType(MediaType.APPLICATION_JSON).with(csrf())
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andDo(print());

    }
}
