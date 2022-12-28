package com.example.bookare.services.ServicesImpl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.example.bookare.entities.Book;
import com.example.bookare.entities.Photo;
import com.example.bookare.models.ResponseDto;
import com.example.bookare.repositories.BookRepository;
import com.example.bookare.repositories.PhotoRepository;
import com.example.bookare.services.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    private final BookRepository bookRepository;
    private final BlobContainerClient blobContainerClient;
    private final Logger LOGGER = LoggerFactory.getLogger(PhotoServiceImpl.class);

    @Override
    public ResponseDto<Photo> getOne(Long id) {
        Optional<Photo> optionalPhoto = photoRepository.findById(id);
        if (optionalPhoto.isPresent()) {
            LOGGER.info("GET PHOTO BY ID");
            return ResponseDto.<Photo>builder()
                    .data(optionalPhoto.get())
                    .isError(false)
                    .message("Photo")
                    .build();
        }
        LOGGER.error("PHOTO NOT FOUND");
        return ResponseDto.<Photo>builder()
                .isError(true)
                .message("Photo not found")
                .build();
    }

    @SneakyThrows
    @Override
    public ResponseDto<?> add(MultipartFile photo, Long book_id) {
        Optional<Book> optionalBook = bookRepository.findByIdAndActiveTrue(book_id);
        if (optionalBook.isEmpty()){
            LOGGER.error("BOOK NOT FOUND");
            return ResponseDto.builder()
                    .isError(true)
                    .message("Book not found")
                    .build();
        }
        String[] split = Objects.requireNonNull(photo.getOriginalFilename()).split("\\.");
        String extension = split[split.length - 1];
        String fileName = UUID.randomUUID() + "." + extension;
        BlobClient blob = blobContainerClient.getBlobClient(fileName);
        blob.upload(photo.getInputStream(),
                photo.getSize(), true);

        Photo photoEntity = Photo.builder()
                .url("https://zuhriddinstorage.blob.core.windows.net/bookare/" + fileName)
                .book(optionalBook.get())
                .name(fileName)
                .build();
        photoRepository.save(photoEntity);
        LOGGER.info("PHOTO ADDED");
        return ResponseDto.builder()
                .isError(false)
                .message("Photo added")
                .build();
    }

    @Override
    public ResponseDto<?> delete(Long id) {
        Optional<Photo> optionalPhoto = photoRepository.findById(id);
        if (optionalPhoto.isEmpty()){
            LOGGER.error("PHOTO NOT FOUND");
            return ResponseDto.builder()
                    .isError(true)
                    .message("Photo not found")
                    .build();
        }
        Photo photo = optionalPhoto.get();
        photo.setActive(false);
        photoRepository.save(photo);
        LOGGER.info("PHOTO DELETED");
        return ResponseDto.builder()
                .isError(false)
                .message("Photo deleted")
                .build();
    }

    @Override
    public ResponseDto<?> download(Long id) {
        Optional<Photo> optionalPhoto = photoRepository.findById(id);
        if (optionalPhoto.isEmpty()){
            LOGGER.error("PHOTO NOT FOUND");
            return ResponseDto.builder()
                    .isError(true)
                    .message("Photo not found")
                    .build();
        }
        Photo photo = optionalPhoto.get();
        BlobClient blob = blobContainerClient.getBlobClient(photo.getName());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blob.downloadStream(outputStream);
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
        LOGGER.info("PHOTO DOWNLOADED");
        return ResponseDto.builder()
                .isError(false)
                .message("Photo downloaded")
                .data(resource)
                .build();
    }
}

