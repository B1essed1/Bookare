package com.example.bookare.services;


import com.example.bookare.entities.Photo;
import com.example.bookare.models.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    ResponseDto<Photo> getOne(Long id);

    ResponseDto<?> add(MultipartFile photo, Long book_id);

    ResponseDto<?> delete(Long id);

    ResponseDto<?> download(Long id);
}
