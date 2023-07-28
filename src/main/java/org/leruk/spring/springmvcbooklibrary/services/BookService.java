package org.leruk.spring.springmvcbooklibrary.services;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.leruk.spring.springmvcbooklibrary.models.Book;
import org.leruk.spring.springmvcbooklibrary.models.Person;
import org.leruk.spring.springmvcbooklibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final EntityManager entityManager;

    @Autowired
    public BookService(BookRepository bookRepository, EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.entityManager = entityManager;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(Sort.by("year"));
        }

        return bookRepository.findAll();
    }

    public List<Book> findWithPagination(int page, int booksPerPage, boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        }

        return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Person getBookOwner(int id) {
        Book book = findOne(id);
        return book.getOwner();
    }

    @Transactional
    public void release(int id) {
        Book book = findOne(id);

        book.setOwner(null);
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Book book = findOne(id);

        book.setOwner(selectedPerson);
    }

    public List<Book> findByTitleStartingWith(String title) {
        return bookRepository.findByTitleStartingWith(title);
    }

    private Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }
}
