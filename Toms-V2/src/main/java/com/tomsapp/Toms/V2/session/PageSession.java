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

   protected String keyword;
   protected String currentPage;
   protected Sort sort;
   protected SelectEnum selectEnum;
   protected String pageSize;



    @PostConstruct
    protected void setUp(){
        Sort.TypedSort<Books> person = Sort.sort(Books.class);
        currentPage="0";
        sort= person.ascending();

    }

    public String getKeyword() {
        return keyword;
    }

    public Sort getSort() {
        return sort;
    }

    public int getCurrentPage() {
        if(currentPage==null||!currentPage.matches("[0-9]+")) return 0;
        else return Integer.parseInt(currentPage);
    }

    public int getPageSize() {
        if(pageSize==null||!pageSize.matches("[0-9]+")) return 20;
        else return Integer.parseInt(pageSize);
    }

    public void setCurrentPageKeywordAndPageSize(String currentPage,String keyword,String pageSize) {
        if(currentPage!=null) this.currentPage = currentPage;
        if (keyword!=null){  this.keyword = keyword;}
        if(pageSize!=null){ this.pageSize = pageSize;}}



    public void refreshPage(){
        currentPage="0";
    }

    public void sortBy(String dropCartEnum){
        if(Arrays.stream(SelectEnum.values()).anyMatch((t) -> t.name().equals(dropCartEnum))){
            selectEnum=SelectEnum.valueOf(dropCartEnum);
            sort = SelectEnum.valueOf(dropCartEnum).getSortByEnum();}}
}


