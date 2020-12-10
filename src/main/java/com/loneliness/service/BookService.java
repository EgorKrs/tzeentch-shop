package com.loneliness.service;

import com.loneliness.entity.BookStatus;
import com.loneliness.entity.domain.Author;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Genre;
import com.loneliness.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class BookService extends CRUDService<Book> {
    private AuthorService authorService;

    public BookService(BookRepository bookRepository, SearchService searchService, AuthorService authorService) {
        repository = bookRepository;
        this.searchService = searchService;
        this.authorService = authorService;
    }

    public Optional<List<Book>> getTop25ByIdIsNotNullOrderByPrintTimeDescPopularityDesc() {
        return ((BookRepository) repository).getTop25ByIdIsNotNullOrderByPrintTimeDesc();
    }

    public Optional<List<Book>> findAllByGenresContains(Genre genreToFind) {
        Optional<List<Book>> optionalBooks = ((BookRepository) repository).findAllByGenresIsNotNull();
        optionalBooks.ifPresent(books -> books.removeIf(book -> book.getGenres().stream().noneMatch(genre -> genre.equals(genreToFind))));
        return optionalBooks;
    }

    public List<Book> findAllByBookStatus(BookStatus bookStatus) {
        List<Book> all = repository.findAll();
        all.removeIf(book -> !book.getBookStatus().equals(bookStatus));
        return all;
    }

    public Book updateBook(Book book) {
        Book oldBook = findById(book.getId()).orElse(new Book());
        if (oldBook.getId() != null) {
            book.setPopularity(oldBook.getPopularity());
            book.setPrintTime(oldBook.getPrintTime());
            book.setRating(oldBook.getRating());
        } else {
            book.setPopularity(0);
            book.setPrintTime(Timestamp.valueOf(LocalDateTime.now()));
            book.setRating(1.0);
        }
        HashSet<Author> authors = new HashSet<>();
        book.getAuthor().forEach(author -> authors.add(authorService.findById(author.getId()).orElse(new Author())));
        book.setAuthor(authors);
        HashSet<Book> relatedBooks = new HashSet<>();
        book.getRelatedBooks().forEach(relatedBook -> relatedBooks.add(findById(relatedBook.getId()).orElse(new Book())));
        book.setRelatedBooks(relatedBooks);
        return save(book);
    }


}
