package com.tomsapp.Toms.V2.enums;

import com.tomsapp.Toms.V2.entity.Books;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SelectEnumTest {

    private List<String> enumString;
     private  List<Sort> personByList;
   private Sort.TypedSort<Books> person;

    @BeforeEach
    void createSortReturn() {
         person = Sort.sort(Books.class);

         personByList=Arrays.asList(
                person.by(Books::getAvailableQuantity).ascending(),
                person.by(Books::getAvailableQuantity).descending(),
                person.by(Books::getAuthors).ascending(),
                person.by(Books::getAuthors).descending(),
                person.by(Books::getTitle).ascending(),
                person.by(Books::getTitle).descending());
    }


    @BeforeEach
    void createEnumList(){
        enumString= Arrays.asList(
                "AVAILABILITYASC",
                "AVAILABILITYDEC",
                "AUTHORASC",
                "AUTHORDEC",
                "TITLEASC",
                "TITLEDEC");
    }

    @Test
    void getSortByEnum() {
        for (int i = 0; i < enumString.size(); i++) {
            //given
            SelectEnum selectEnum=SelectEnum.valueOf(enumString.get(i));
            //when

            //then
            assertEquals(selectEnum.getSortByEnum(),personByList.get(i));

        }


    }


}