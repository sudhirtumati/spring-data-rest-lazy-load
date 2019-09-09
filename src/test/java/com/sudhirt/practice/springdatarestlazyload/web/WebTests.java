package com.sudhirt.practice.springdatarestlazyload.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import javax.transaction.Transactional;

import com.sudhirt.practice.springdatarestlazyload.DemoApplication;
import com.sudhirt.practice.springdatarestlazyload.entity.Author;
import com.sudhirt.practice.springdatarestlazyload.entity.Book;
import com.sudhirt.practice.springdatarestlazyload.repository.AuthorRepository;
import com.sudhirt.practice.springdatarestlazyload.repository.BookRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@Transactional
public class WebTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        Author author = Author.builder().firstName("Author First 1").lastName("Author Last 1")
                .dateOfBirth(LocalDate.of(1982, 1, 1)).build();
        author = authorRepository.save(author);
        Book book = Book.builder().isbn("isbn1").name("name1").price(10.99d).publishedDate(LocalDate.of(2012, 1, 16))
                .author(author).build();
        bookRepository.save(book);
        book = Book.builder().isbn("isbn2").name("name2").price(15.99d).publishedDate(LocalDate.of(2015, 8, 23))
                .author(author).build();
        bookRepository.save(book);
    }

    @Test
    public void getAllBooks() throws Exception {
        mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$._embedded.books", hasSize(2)))
                .andExpect(jsonPath("$._embedded.books[0].author").doesNotExist());
    }

    @Test
    public void getAllAuthors() throws Exception {
        mockMvc.perform(get("/authors")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$._embedded.authors", hasSize(1)))
                .andExpect(jsonPath("$._embedded.authors[0].books").doesNotExist());
    }
}