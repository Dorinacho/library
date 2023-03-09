package com.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Size(max = 17)
    private String isbn;
    @Column
    private String title;

    @ManyToOne(targetEntity = Author.class)
    @JoinColumn(name = "author")
    private Author author;
    @Column(name = "available_copies")
    private int availability;

    @Column
    private String description;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private Set<Loan> loans = new LinkedHashSet<>();

    public Book(Long id, String isbn, String title, Author author, int availability, String description) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.availability = availability;
        this.description = description;
    }

    public Book(String isbn, String title, Author author, int availability, String description) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.availability = availability;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", availability=" + availability +
                ", loans=" + loans +
                '}';
    }
}
