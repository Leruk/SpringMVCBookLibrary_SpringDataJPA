package org.leruk.spring.springmvcbooklibrary.repositories;

import org.leruk.spring.springmvcbooklibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
