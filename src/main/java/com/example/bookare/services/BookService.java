package com.example.bookare.services;

import com.example.bookare.models.BookDto;
import com.example.bookare.models.BookFrontDto;
import com.example.bookare.models.ResponseDto;

public interface BookService {
    ResponseDto<?> getAll();

    ResponseDto<BookFrontDto> getOne(Long id);

    ResponseDto<?> add(BookDto bookDto);

    ResponseDto<?> update(Long id, BookDto bookDto);

    ResponseDto<?> delete(Long id);
}
