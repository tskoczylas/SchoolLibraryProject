package com.tomsapp.Toms.V2.session;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.enums.SelectEnum;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PageSessionTest {

    @Test
    void setUpShouldSetCurrentPageTo0AndSortToAscending() {
        //given
        PageSession pageSession = new PageSession();
        Sort.TypedSort<Books> person = Sort.sort(Books.class);

        //when
        pageSession.setUp();
        //then
        assertEquals(pageSession.currentPage,"0");
        assertEquals(pageSession.sort,person.ascending());


    }


    @Test
    void getCurrentPageShouldReturnCurrentPageWhenCPIsNotNullAndIsInteger() {
        //given
        PageSession pageSession = new PageSession();
        pageSession.currentPage="2";

        //when
        int currentPage = pageSession.getCurrentPage();
        //then
        assertEquals(currentPage,2);
    }

    @Test
    void getCurrentPageShouldReturn0WhenCurrentPageIsNull() {
        //given
        PageSession pageSession = new PageSession();
        pageSession.currentPage=null;

        //when
        int currentPage = pageSession.getCurrentPage();
        //then
        assertEquals(currentPage,0);
    }
    @Test
    void getCurrentPageShouldReturn0WhenCurrentPageIsNotNull() {
        //given
        PageSession pageSession = new PageSession();
        pageSession.currentPage="sample";

        //when
        int currentPage = pageSession.getCurrentPage();
        //then
        assertEquals(currentPage,0);
    }

    @Test
    void getPageSizeShouldReturnPageSessionWhenPSIsNotNullAndIsInteger() {
        //given
        PageSession pageSession = new PageSession();
        pageSession.pageSize="10";

        //when
        int pageSize = pageSession.getPageSize();
        //then
        assertEquals(pageSize,10);
    }

    @Test
    void getPageSessionShouldReturn20WhenCurrentPageIsNull() {
        //given
        PageSession pageSession = new PageSession();
        pageSession.currentPage=null;

        //when
        int pageSize = pageSession.getPageSize();
        //then
        assertEquals(pageSize,20);
    }
    @Test
    void getPageSessionShouldReturn20WhenCurrentPageIsNotNull() {
        //given
        PageSession pageSession = new PageSession();
        pageSession.pageSize="sample";

        //when
        int pageSize = pageSession.getPageSize();
        //then
        assertEquals(pageSize,20);
    }



    @Test
    void setCurrentPageKeywordAndPageSizeShouldSetAllWhenAllNotNull() {
        //given
        PageSession pageSession = new PageSession();
        //when
        pageSession.setCurrentPageKeywordAndPageSize("10","sample","2");
        //then
        assertEquals(pageSession.currentPage,"10");
        assertEquals(pageSession.keyword,"sample");
        assertEquals(pageSession.pageSize,"2");
    }

    @Test
    void setCurrentPageKeywordAndPageSizeShouldSetKeywordAndPageSizeWhenCurrentPageNull() {
        //given
        PageSession pageSession = new PageSession();
        pageSession.currentPage="22";
        //when
        pageSession.setCurrentPageKeywordAndPageSize(null,"sample","2");
        //then
        assertEquals(pageSession.currentPage,"22");
        assertEquals(pageSession.keyword,"sample");
        assertEquals(pageSession.pageSize,"2");
    }

    @Test
    void setCurrentPageKeywordAndPageSizeShouldSetCurrentPageAndPageSizeWhenKeywordIsNull() {
        //given
        PageSession pageSession = new PageSession();
        pageSession.keyword="test";
        //when
        pageSession.setCurrentPageKeywordAndPageSize("22",null,"2");
        //then
        assertEquals(pageSession.currentPage,"22");
        assertEquals(pageSession.keyword,"test");
        assertEquals(pageSession.pageSize,"2");
    }

    @Test
    void setCurrentPageKeywordAndPageSizeShouldSetCurrentPageAndKeywordWhenPageSizeIsNull() {
        //given
        PageSession pageSession = new PageSession();
        pageSession.pageSize="4";
        //when
        pageSession.setCurrentPageKeywordAndPageSize("22","keyword",null);
        //then
        assertEquals(pageSession.currentPage,"22");
        assertEquals(pageSession.keyword,"keyword");
        assertEquals(pageSession.pageSize,"4");
    }

    @Test
    void refreshPageShouldSetCurrentPageTo0() {
        //given
        PageSession pageSession = new PageSession();
        pageSession.currentPage="4";
        //when
        pageSession.refreshPage();
        //then
        assertEquals(pageSession.currentPage,"0");

    }

    @Test
    void sortByShouldSetSelectEnumAndExecuteGetSortByEnumWhenInputIsSelectEnum() {
        //given
        PageSession pageSession = new PageSession();

        Random random = new Random();

        SelectEnum[] values = SelectEnum.values();
        int i = random.nextInt(values.length);
         SelectEnum randomSelectEnum = values[i];

        String dropCartEnum= randomSelectEnum.name();
        //when
        pageSession.sortBy(dropCartEnum);
        //then
        assertEquals(pageSession.selectEnum,randomSelectEnum);
        assertEquals(pageSession.sort,randomSelectEnum.getSortByEnum());
    }

    @Test
    void sortByShouldDoNothingWhenInputIsNotSelectEnum() {
        //given
        PageSession pageSession = new PageSession();


        String dropCartEnum= "select";
        //when
        pageSession.sortBy(dropCartEnum);
        //then
        assertNull(pageSession.selectEnum);
        assertNull(pageSession.sort);
    }




}