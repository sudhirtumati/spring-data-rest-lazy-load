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
                Book book1 = Book.builder().isbn("isbn1").title("title1").price(10.99d)
                                .publishedDate(LocalDate.of(2012, 1, 16)).author(author).build();
                author.addBook(book1);
                author = authorRepository.save(author);
                Book book2 = Book.builder().isbn("isbn2").title("title2").price(15.99d).publishedDate(LocalDate.of(2015, 8, 23))
                                .author(author).build();
                author.addBook(book2);
                author = authorRepository.save(author);
                bookRepository.save(book1);
                bookRepository.save(book2);
        }

        @Test
        public void getAllBooks() throws Exception {
                mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk())
                                .andExpect(jsonPath("$._embedded.books", hasSize(2)))
                                .andExpect(jsonPath("$._embedded.books[0].author").doesNotExist());
        }

        @Test
        public void getAllAuthors() throws Exception {
                mockMvc.perform(get("/authors")).andDo(print()).andExpect(status().isOk())
                                .andExpect(jsonPath("$._embedded.authors", hasSize(1)))
                                .andExpect(jsonPath("$._embedded.authors[0].books").doesNotExist());
        }

        @Test
        public void getAllBooksWithAuthors() throws Exception {
                mockMvc.perform(get("/books?projection=withAuthor")).andDo(print()).andExpect(status().isOk())
                                .andExpect(jsonPath("$._embedded.books", hasSize(2)))
                                .andExpect(jsonPath("$._embedded.books[0].author").exists());
        }

        @Test
        public void getAuthorWithBooks() throws Exception {
                mockMvc.perform(get("/authors?projection=withBooks")).andDo(print()).andExpect(status().isOk())
                                .andExpect(jsonPath("$._embedded.authors", hasSize(1)))
                                .andExpect(jsonPath("$._embedded.authors[0].books").exists())
                                .andExpect(jsonPath("$._embedded.authors[0].books", hasSize(2)));
        }
}