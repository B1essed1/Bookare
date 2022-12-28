package com.example.bookare.controllers;

import com.example.bookare.entities.Photo;
import com.example.bookare.models.ResponseDto;
import com.example.bookare.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/photo")
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        ResponseDto<Photo> response = photoService.getOne(id);
        return ResponseEntity.status(response.getIsError() ? 404 : 200).body(response);
    }

    @PostMapping("/{book_id}")
    public ResponseEntity<?> add(@RequestBody MultipartFile photo, @PathVariable Long book_id) {
        ResponseDto<?> response = photoService.add(photo, book_id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ResponseDto<?> response = photoService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> download(@PathVariable Long id) {
        ResponseDto<?> response = photoService.download(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photoService.getOne(id).getData().getName() + "\"");
        return ResponseEntity.status(response.getIsError() ? 404 : 200).contentType(MediaType.APPLICATION_OCTET_STREAM).headers(headers).body(response);
    }
}
