package com.example.bookare.services.ServicesImpl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.example.bookare.entities.Book;
import com.example.bookare.entities.Photo;
import com.example.bookare.entities.Users;
import com.example.bookare.models.BookDto;
import com.example.bookare.models.BookFrontDto;
import com.example.bookare.models.ResponseDto;
import com.example.bookare.repositories.BookRepository;
import com.example.bookare.repositories.GenreRepository;
import com.example.bookare.repositories.PhotoRepository;
import com.example.bookare.repositories.UsersRepository;
import com.example.bookare.services.BookService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final UsersRepository usersRepository;
    private final PhotoRepository photoRepository;
    private final BlobContainerClient blobContainerClient;

    @Override
    public ResponseDto<?> getAll() {

        List<Book> books = bookRepository.findAllByActiveTrue();
        List<BookFrontDto> bookFrontList = new ArrayList<>();
        books.forEach(book -> {
            BookFrontDto bookFrontDto = BookFrontDto.builder()
                    .book(book)
                    .userId(book.getUsers().getId())
                    .photoUrls(
                            photoRepository.findAllByBookIdAndActiveTrue(book.getId()).stream()
                                    .map(Photo::getUrl).collect(Collectors.toList()))
                    .build();
            bookFrontList.add(bookFrontDto);
        });
        LOGGER.info("GET ALL BOOKS");
        return ResponseDto.builder()
                .data(bookFrontList)
                .isError(false)
                .message("All books")
                .build();
    }

    @Override
    public ResponseDto<BookFrontDto> getOne(Long id) {
        Optional<Book> optionalBook = bookRepository.findByIdAndActiveTrue(id);
        if (optionalBook.isEmpty()) {
            LOGGER.info("THERE IS NO SUCH A BOOK WITH GIVEN ID");
            return ResponseDto.<BookFrontDto>builder()
                    .message("Book not found")
                    .isError(true)
                    .build();
        }
        Book book = optionalBook.get();
        BookFrontDto frontDto = BookFrontDto.builder()
                .book(book)
                .userId(book.getUsers().getId())
                .photoUrls(photoRepository.findAllByBookIdAndActiveTrue(book.getId()).stream()
                        .map(Photo::getUrl)
                        .collect(Collectors.toList())).build();

        LOGGER.info("GET ONE BOOK");
        return ResponseDto.<BookFrontDto>builder()
                .message("One book")
                .isError(false)
                .data(frontDto)
                .build();
    }

    @SneakyThrows
    @Override
    public ResponseDto<?> add(BookDto bookDto) {
        Optional<Users> optionalUsers = usersRepository.findById(bookDto.getUser_id());
        if (optionalUsers.isEmpty()) {
            LOGGER.info("THERE IS NO SUCH A USER WITH GIVEN ID");
            return ResponseDto.builder()
                    .message("User not found")
                    .isError(true)
                    .build();
        }
        Book book = Book.builder()
                .name(bookDto.getName())
                .authors(bookDto.getAuthors())
                .description(bookDto.getDescription())
                .pages(bookDto.getPages())
                .language(bookDto.getLanguage())
                .quantity(bookDto.getQuantity())
                .type(bookDto.getType())
                .price(bookDto.getPrice())
                .genres(genreRepository.findAll().stream().filter(genre ->
                        bookDto.getGenre_ids().contains(genre.getId())).toList())
                .users(optionalUsers.get())
                .build();
        Book savedBook = bookRepository.save(book);
        for (MultipartFile photo : bookDto.getPhotos()) {
            String[] split = Objects.requireNonNull(photo.getOriginalFilename()).split("\\.");
            String extension = split[split.length - 1];
            String fileName = UUID.randomUUID() + "." + extension;
            Photo savedPhoto = Photo.builder()
                    .book(savedBook)
                    .url(upload(photo, fileName))
                    .name(fileName)
                    .build();
            photoRepository.save(savedPhoto);
        }
        LOGGER.info("ADD BOOK");
        return ResponseDto.builder()
                .message("Book added")
                .isError(false)
                .build();

    }

    @Override
    public ResponseDto<?> update(Long id, BookDto bookDto) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Optional<Users> optionalUsers = usersRepository.findById(bookDto.getUser_id());
        if (optionalBook.isEmpty()) {
            LOGGER.info("THERE IS NO SUCH A BOOK WITH GIVEN ID");
            return ResponseDto.builder()
                    .message("Book not found")
                    .isError(true)
                    .build();
        }
        if (optionalUsers.isEmpty()) {
            LOGGER.info("THERE IS NO SUCH A USER WITH GIVEN ID");
            return ResponseDto.builder()
                    .message("User not found")
                    .isError(true)
                    .build();
        }
        Book book = optionalBook.get();
        book.setName(bookDto.getName());
        book.setAuthors(bookDto.getAuthors());
        book.setDescription(bookDto.getDescription());
        book.setPages(bookDto.getPages());
        book.setLanguage(bookDto.getLanguage());
        book.setQuantity(bookDto.getQuantity());
        book.setType(bookDto.getType());
        book.setPrice(bookDto.getPrice());
        book.setGenres(genreRepository.findAll().stream().filter(genre ->
                bookDto.getGenre_ids().contains(genre.getId())).toList());
        book.setUsers(optionalUsers.get());
        bookRepository.save(book);
        LOGGER.info("UPDATE BOOK");
        return ResponseDto.builder()
                .message("Book updated")
                .isError(false)
                .build();
    }

    @Override
    public ResponseDto<?> delete(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            LOGGER.info("THERE IS NO SUCH A BOOK WITH GIVEN ID");
            return ResponseDto.builder()
                    .message("Book not found")
                    .isError(true)
                    .build();
        }
        Book book = optionalBook.get();
        book.setActive(false);
        bookRepository.save(book);
        LOGGER.info("DELETE BOOK");
        return ResponseDto.builder()
                .message("Book deleted")
                .isError(false)
                .build();
    }

    public String upload(MultipartFile multipartFile, String fileName) throws IOException {
        BlobClient blob = blobContainerClient.getBlobClient(fileName);
        blob.upload(multipartFile.getInputStream(),
                multipartFile.getSize(), true);

        return "https://zuhriddinstorage.blob.core.windows.net/bookare/" + fileName;
    }

}
