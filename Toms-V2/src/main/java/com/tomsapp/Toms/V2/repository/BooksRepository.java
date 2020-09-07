package com.tomsapp.Toms.V2.repository;

import com.tomsapp.Toms.V2.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepository extends JpaRepository<Books,Integer> {

    public List<Books> findByAuthorContains(String search);

}
