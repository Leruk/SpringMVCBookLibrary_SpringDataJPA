package org.leruk.spring.springmvcbooklibrary.services;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.leruk.spring.springmvcbooklibrary.models.Book;
import org.leruk.spring.springmvcbooklibrary.models.Person;
import org.leruk.spring.springmvcbooklibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Book> findAll() {
        return bookRepository.findAll();
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

    private Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }
}
