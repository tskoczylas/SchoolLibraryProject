package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.enums.SelectEnum;
import com.tomsapp.Toms.V2.repository.BooksRepository;
import com.tomsapp.Toms.V2.service.BooksService;
import com.tomsapp.Toms.V2.service.StudentServiceInt;
import com.tomsapp.Toms.V2.session.BasketSession;
import com.tomsapp.Toms.V2.session.PageSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

class BookControllerTest {
    @Mock
    BooksService booksService;
    @Mock
    StudentServiceInt studentServiceInt;
    @Mock
    BasketSession basketSession;
    @Mock
    PageSession pageSession;


    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;


    @BeforeEach
    public void setup(){

        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();}




    @Test
    void bookShouldProvideAllModelsAndReceiveAllParamsSetCurrentPageKeywordPageSizeAndSortByAndExecuteRefreshPageMet() throws Exception {

        //given

        List<Books> booksList = new ArrayList<>();
        booksList.add(new Books());
        //when
        PageImpl<Books> pagedResponse = mock(PageImpl.class);
        when(pagedResponse.getTotalPages()).thenReturn(4);
        when(pagedResponse.get()).thenReturn(booksList.stream());
       when(booksService.findAllOrFindByKeyword()).thenReturn(pagedResponse);

       //then


        MockHttpServletRequestBuilder updateDetails = get("/book/")
                .flashAttr("selectEnum","MIN")
                .param("currentPage","2")
                .param("keyword","search")
                .param("pageSize","9");

        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("book"))
                 .andExpect(model().attribute("booksList",booksList ))
                .andExpect(model().attribute("pageSession", pageSession))
                .andExpect(model().attribute("lastPage", 3))
                .andExpect(model().attribute("basketSession", basketSession))
                .andExpect(model().attribute("selectEnum", new ArrayList<>(Arrays.asList(SelectEnum.values()))));

        verify(pageSession).setCurrentPageKeywordAndPageSize("2","search","9");
        verify(pageSession).sortBy("MIN");
        verify(pageSession).refreshPage();

    }

    @Test
    void bookShouldProvideAllModelsAndReceiveAllParamsSetCurrentPagePageSizeAndSortByAndDoNotExecuteRefreshPageMet() throws Exception {

        //given

        List<Books> booksList = new ArrayList<>();
        booksList.add(new Books());
        //when
        PageImpl<Books> pagedResponse = mock(PageImpl.class);
        when(pagedResponse.getTotalPages()).thenReturn(4);
        when(pagedResponse.get()).thenReturn(booksList.stream());
        when(booksService.findAllOrFindByKeyword()).thenReturn(pagedResponse);

        //then


        MockHttpServletRequestBuilder updateDetails = get("/book/")
                .flashAttr("selectEnum","MIN")
                .param("currentPage","2")
                .param("pageSize","9");

        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("book"))
                .andExpect(model().attribute("booksList",booksList ))
                .andExpect(model().attribute("pageSession", pageSession))
                .andExpect(model().attribute("lastPage", 3))
                .andExpect(model().attribute("basketSession", basketSession))
                .andExpect(model().attribute("selectEnum", new ArrayList<>(Arrays.asList(SelectEnum.values()))));

        verify(pageSession).setCurrentPageKeywordAndPageSize("2",null,"9");
        verify(pageSession).sortBy("MIN");
        verifyNoMoreInteractions(pageSession);
    }

    @Test
    void addShouldAddBookIdOrRemoveCartBookIdToBasketSessionMetWhenPresent() throws Exception {

        //given
        //when
        //then

        MockHttpServletRequestBuilder updateDetails = get("/book/add/")
                .param("bookId","2")
                .param("removeCartBookId","9");

        mockMvc.perform(updateDetails)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));

        verify(basketSession).addBookToBasket("2");
        verify(basketSession).removeBookFromBasket("9");
    }

    @Test
    void addShouldAddNullToBasketSessionMetWhenNotPresent() throws Exception {

        //given
        //when
        //then

        MockHttpServletRequestBuilder updateDetails = get("/book/add/");

        mockMvc.perform(updateDetails)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));

        verify(basketSession).addBookToBasket(null);
        verify(basketSession).removeBookFromBasket(null);
    }

}