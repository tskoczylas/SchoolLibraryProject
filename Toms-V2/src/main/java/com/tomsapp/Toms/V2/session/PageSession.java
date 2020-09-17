package com.tomsapp.Toms.V2.session;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.enums.SelectEnum;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SessionScope
@Component

public class PageSession {

   private String keyword;
   private String currentPage;
   private Sort sort;
   private SelectEnum selectEnum;



    @PostConstruct
    void setUp(){
        Sort.TypedSort<Books> person = Sort.sort(Books.class);
        currentPage="0";
        sort= person.ascending();

    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        if (keyword!=null){  this.keyword = keyword;}
    }

    public String getCurrentPage() {
        if(currentPage==null) return "0";
        else return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        if(currentPage!=null) this.currentPage = currentPage;
    }

    public void setCurrentPageAndKeyword(String currentPage,String keyword) {
        if(currentPage!=null) this.currentPage = currentPage;
        if (keyword!=null){  this.keyword = keyword;}
    }

    public Sort getSort() {
        return sort;
    }

    public void refreshPage(){
        currentPage="0";
    }

    public SelectEnum getSelectEnum() {
        return selectEnum;
    }

    public void sortBy(String dropCartEnum){
        if(!dropCartEnum.isEmpty()){


            this.selectEnum=SelectEnum.valueOf(dropCartEnum);
            SelectEnum selectEnum = SelectEnum.valueOf(dropCartEnum);
            sort = selectEnum.getSortByEnum();}}
}


