package org.leruk.spring.springmvcbooklibrary.repositories;

import jdk.jfr.Registered;
import org.leruk.spring.springmvcbooklibrary.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByFullName(String fullName);
}