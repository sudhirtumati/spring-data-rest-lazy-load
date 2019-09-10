package com.sudhirt.practice.springdatarestlazyload.entity.projections;

import java.time.LocalDate;
import java.util.List;

import com.sudhirt.practice.springdatarestlazyload.entity.Author;
import com.sudhirt.practice.springdatarestlazyload.entity.Book;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "withBooks", types = { Author.class })
public interface AuthorWithBooksProjection {

    String getId();
    String getFirstName();
    String getLastName();
    LocalDate getDateOfBirth();
    List<Book> getBooks();
}