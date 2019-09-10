package com.sudhirt.practice.springdatarestlazyload.entity.projections;

import com.sudhirt.practice.springdatarestlazyload.entity.Author;
import com.sudhirt.practice.springdatarestlazyload.entity.Book;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "withAuthor", types = { Book.class })
public interface BookWithAuthorProjection {

    String getId();
    String getTitle();
    String getIsbn();
    Double getPrice();
    Author getAuthor();
}