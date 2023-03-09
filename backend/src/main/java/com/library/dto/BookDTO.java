package com.library.dto;


import lombok.Getter;

@Getter
public class BookDTO {
    private Long id;

    private String isbn;

    private String title;

    private String author;

    private int availability;

    private String description;


}
