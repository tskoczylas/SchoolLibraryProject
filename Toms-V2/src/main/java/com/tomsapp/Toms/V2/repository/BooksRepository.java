package com.tomsapp.Toms.V2.repository;

import com.tomsapp.Toms.V2.entity.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BooksRepository extends JpaRepository<Books,Integer> {



     ///Page<Books> findAllByTitleOrAuthorsOrIsbnAllIgnoreCase(String searchFieldTitle,
       //                                                             String searchFieldAuthors,
       //                                                             String searchFieldIsbn, Pageable pageable );

     @Query(value = "select u from Books u where u.authors like %:keyword% or u.isbn like %:keyword% or u.title like %:keyword%")
     Page<Books> findIt(@Param("keyword") String keyword, Pageable pageable );

}
