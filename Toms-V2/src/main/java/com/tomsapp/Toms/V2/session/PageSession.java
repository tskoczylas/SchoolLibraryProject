package com.tomsapp.Toms.V2.session;

import com.tomsapp.Toms.V2.entity.Books;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SessionScope
@Component

public class PageSession {

   private String keyword;
   private String currentPage;


    @PostConstruct
    void setUp(){
        currentPage="0";

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


    public void refreshPage(){
        currentPage="0";
    }
}


