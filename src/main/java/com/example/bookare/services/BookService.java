package com.example.bookare.services;

import com.example.bookare.models.BookDto;
import com.example.bookare.models.BookFrontDto;
import com.example.bookare.models.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BookService {
    ResponseDto<?> getAll();

    ResponseDto<BookFrontDto> getOne(Long id);

    ResponseDto<?> add(BookDto bookDto);

    ResponseDto<?> update(Long id, BookDto bookDto);

    ResponseDto<?> delete(Long id);

    public String upload(MultipartFile multipartFile, String fileName) throws IOException;
}
