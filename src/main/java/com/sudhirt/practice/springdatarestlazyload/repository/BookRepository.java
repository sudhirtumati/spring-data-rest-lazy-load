package com.sudhirt.practice.springdatarestlazyload.repository;

import com.sudhirt.practice.springdatarestlazyload.entity.Book;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, String> {

}