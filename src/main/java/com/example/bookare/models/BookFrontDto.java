package com.example.bookare.models;

import com.example.bookare.entities.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BookFrontDto {
    private Book book;
    private List<String> photoUrls;
    private Long userId;

}
