package com.tomsapp.Toms.V2.enums;

import com.tomsapp.Toms.V2.entity.Books;
import org.springframework.data.domain.Sort;

public enum  SelectEnum {
    DEFAULT("Default sort"),
    AVAILABILITYASC("Sort by availability(0-9)"),
    AVAILABILITYDEC("Sort by availability(9-0)"),
    AUTHORASC("Sort by author(A-Z)"),
    AUTHORDEC("Sort by author(Z-A)"),
    TITLEASC("Sort by title(A-Z)"),
    TITLEDEC("Sort by title(Z-A)");

    String description;

    SelectEnum(String description) {
        this.description = description; }

    public String getDescription() {
        return description;
    }


    public Sort getSortByEnum() {
        Sort.TypedSort<Books> person = Sort.sort(Books.class);

        if (this == AVAILABILITYASC) return person.by(Books::getTotalNumber).ascending();
        if (this == AVAILABILITYDEC) return person.by(Books::getTotalNumber).descending();
        if (this == AUTHORASC) return person.by(Books::getAuthors).ascending();
        if (this == AUTHORDEC) return person.by(Books::getAuthors).descending();
        if (this == TITLEASC) return person.by(Books::getTitle).ascending();
        if (this == TITLEDEC) return person.by(Books::getTitle).descending();
        else return person.ascending();

    }


}
