package com.loneliness.service;

import com.loneliness.entity.BookStatus;
import com.loneliness.entity.domain.Author;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Genre;
import com.loneliness.entity.domain.User;
import com.loneliness.exception.NotFoundException;
import com.loneliness.repository.BookRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


@Transactional
@Service
public class BookService extends CRUDService<Book> {
    private AuthorService authorService;
    private UserService userService;
    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public BookService(BookRepository bookRepository, SearchService searchService, AuthorService authorService, UserService userService) {
        repository = bookRepository;
        this.searchService = searchService;
        this.authorService = authorService;
        this.userService = userService;
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

    public List<Book> filterByInputParam(Map<BookStatus, Integer> bookStatuses, Map<Genre, Integer> genres,
                                         Integer userId, Boolean isRandom, String authorName, Boolean isPopular,
                                         String[] timePeriod, Integer[] rating, boolean isLikePast) throws ParseException {
        List<Book> all = repository.findAll();
        // get 5 random book
        if (isRandom) {
            User user = userService.findById(userId).orElseThrow(NotFoundException::new);
            List<Book> randomBooksList = new LinkedList<>();
            for (int i = 0; i < 3; i++) {
                int randomVal = random.nextInt(0, all.size());
                Book book = all.get(randomVal);
                if (!user.getBooks().contains(book) && !randomBooksList.contains(book)) {
                    randomBooksList.add(book);
                } else {
                    i -= 1;
                }
            }
            return randomBooksList;
        }
        // get book by authors
        if (authorName != null && !authorName.isBlank()) {
            all.removeIf(book -> isContainAuthorName(book.getAuthor(), authorName));
        }
        // get book by timePeriod
        if (timePeriod != null && !timePeriod[0].isBlank() && !timePeriod[1].isBlank()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Timestamp low = new Timestamp((dateFormat.parse(timePeriod[0])).getTime());
            Timestamp high = new Timestamp((dateFormat.parse(timePeriod[1])).getTime());
//            Timestamp low = Timestamp.valueOf(timePeriod[0]);
//            Timestamp high = Timestamp.valueOf(timePeriod[1]);
            all.removeIf(book -> !isBetween(book.getPrintTime(), low, high));
        }
        // get book by rating
        if (rating != null) {
            all.removeIf(book -> !isBetween(book.getRating().intValue(), rating[0], rating[1]));
        }
        // get popular book
        if (isPopular) {
            all.removeIf(book -> book.getPopularity().compareTo(50) < 0);
        }
        // get genres book
        if (genres != null) {
            for (Genre genre : genres.keySet()) {
                if (genres.get(genre).equals(1)) {
                    all.removeIf(book -> !book.getGenres().contains(genre));
                } else {
                    all.removeIf(book -> book.getGenres().contains(genre));
                }
            }
        }
        // get bookStatuses book
        if (bookStatuses != null) {
            for (BookStatus bookStatus :
                    bookStatuses.keySet()) {
                if (bookStatuses.get(bookStatus).equals(1)) {
                    all.removeIf(book -> !book.getGenres().contains(bookStatus));
                } else {
                    all.removeIf(book -> book.getGenres().contains(bookStatus));
                }
                all.removeIf(book -> !book.getBookStatus().equals(bookStatus));
            }
        }
        // get related book
        if (userId != null && isLikePast) {
            User user = userService.findById(userId).orElseThrow(NotFoundException::new);
            for (Book book :
                    user.getBooks()) {
                all.removeIf(bookRelatedTo -> !bookRelatedTo.getRelatedBooks().contains(book));
            }
        }


        return all;
    }


    public Book updateBook(@NotNull Book book) {
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

    private boolean isBetween(Timestamp date, Timestamp low, Timestamp high) {
        return date.after(low) && date.before(high);

    }

    private boolean isBetween(Integer rating, Integer low, Integer high) {
        return rating >= low && rating <= high;

    }

    private boolean isContainAuthorName(Set<Author> authorSet, String nameToFind) {
        for (Author author :
                authorSet) {
            if (author.getName().equals(nameToFind)) {
                return true;
            }
        }
        return false;
    }


}
