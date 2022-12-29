package com.example.bookare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class BookDto {

    private String name;
    private String authors;
    private String description;
    private int pages;
    private String language;
    private int quantity;
    private int type;
    private float price;
    private List<Long> genre_ids;
    private Long user_id;
    private List<MultipartFile> photos;
}
