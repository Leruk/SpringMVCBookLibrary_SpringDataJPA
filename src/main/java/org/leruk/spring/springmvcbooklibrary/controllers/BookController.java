package org.leruk.spring.springmvcbooklibrary.controllers;

import jakarta.validation.Valid;
import org.leruk.spring.springmvcbooklibrary.models.Book;
import org.leruk.spring.springmvcbooklibrary.models.Person;
import org.leruk.spring.springmvcbooklibrary.services.BookService;
import org.leruk.spring.springmvcbooklibrary.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }


    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear) {
        List<Book> books = new ArrayList<>();

        if (page == null || booksPerPage == null) {
            books = bookService.findAll(sortByYear);
        } else {
            books = bookService.findAll(page, booksPerPage, sortByYear);
        }
        model.addAttribute("books", books);

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));

        Person bookOwner = bookService.getBookOwner(id);

        if (bookOwner != null) {
            model.addAttribute("owner", bookOwner);
        } else {
            model.addAttribute("people", personService.findAll());
        }

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOne(id));

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }
}
