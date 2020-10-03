package com.tomsapp.Toms.V2.dto;

import javax.persistence.Column;
import java.time.LocalDate;

public class BookDto {

    String title;
    String isbn;
    String pageCount;
    LocalDate publishedDate;
    String thumbnailUrl;
    String shortDescription;
    String longDescription;
    String status;
    String authors;
    String categories;
    Integer availableQuantity;

}
