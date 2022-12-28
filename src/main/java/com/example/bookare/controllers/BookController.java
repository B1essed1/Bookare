package com.example.bookare.controllers;

import com.example.bookare.models.BookDto;
import com.example.bookare.models.BookFrontDto;
import com.example.bookare.models.ResponseDto;
import com.example.bookare.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<?> getBooks(){
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        ResponseDto<BookFrontDto> response = bookService.getOne(id);
        return ResponseEntity.status(response.getIsError() ? 404  : 200).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@ModelAttribute BookDto bookDto){
        ResponseDto<?> response = bookService.add(bookDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @ModelAttribute BookDto bookDto){
        ResponseDto<?> response = bookService.update(id, bookDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ResponseDto<?> response = bookService.delete(id);
        return ResponseEntity.ok(response);
    }


}
