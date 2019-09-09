package com.sudhirt.practice.springdatarestlazyload.repository;

import com.sudhirt.practice.springdatarestlazyload.entity.Author;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "authors", path = "authors")
public interface AuthorRepository extends PagingAndSortingRepository<Author, String> {

}