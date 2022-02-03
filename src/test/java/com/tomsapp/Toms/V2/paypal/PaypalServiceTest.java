package com.tomsapp.Toms.V2.paypal;

import com.paypal.api.payments.Item;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Student;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.tomsapp.Toms.V2.paypal.PaypalServiceImp.createItem;
import static com.tomsapp.Toms.V2.utils.HostUtils.getHost;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.jupiter.api.Assertions.*;

class PaypalServiceTest {
    /*

    PaypalService paypalService;



    BorrowDto borrow;

    @BeforeEach
    void createBorrows(){
         borrow = new BorrowDto();
         borrow.setId(1);
        borrow.setPricePerItem(0.3);
        borrow.setDaysBorrow(2);
        borrow.setOverDueFee(2);
        borrow.setTotalCost(0.6);
        Student student = new Student();
        student.setFirstName("FirstName");
        student.setLastName("LastName");
        student.setEmail("email");

        Adress adress = new Adress();
        adress.setPostCode("postCode");
        adress.setAddressFirstLine("first");
        adress.setAddressSecondLine("second");
        adress.setTelephone("1");
        student.setAdresses(adress);
        Books books = new Books();
        books.setTitle("abc");
        Books books2 = new Books();
        books2.setTitle("bcc");
        List<Books> booksList=new ArrayList<>();
        booksList.add(books);
        booksList.add(books2);

        borrow.setBooks(booksList);
        borrow.setStudent(student);


    }

    @Test
    void createItemShouldCreateListOfItemFromBorrow() {
        //given
        //when
        List<Item> item = createItem(borrow);
        //then
        assertEquals(item.get(0).getQuantity(),"1");
        assertEquals(item.get(0).getCurrency(),"GBP");
        assertEquals(item.get(0).getName(),"abc");
        assertEquals(item.get(0).getPrice(),"0.60");
        assertThat(item,hasSize(2));
    }

    @Test

    void createPaymentShouldReturnPaymentMapFromBorrow() throws PayPalRESTException {
        Payment payment = paypalService.createPaymentHttpRes(borrow, "/success", "/cancel");
        assertEquals(payment.getTransactions().get(0).getAmount().getTotal(),"0.60");
        assertEquals(payment.getTransactions().get(0).getAmount().getCurrency(),"GBP");
        assertEquals(payment.getTransactions().get(0).getAmount().getDetails().getSubtotal(),"0.60");
        assertEquals(payment.getTransactions().get(0).getAmount().getDetails().getShipping(),"0");

        assertEquals(payment.getTransactions().get(0).getItemList().getShippingAddress().getCountryCode(),"UK");
        assertEquals(payment.getTransactions().get(0).getItemList().getShippingAddress().getLine1(),"first");
        assertEquals(payment.getTransactions().get(0).getItemList().getShippingAddress().getLine2(),"second");
        assertEquals(payment.getTransactions().get(0).getItemList().getShippingAddress().getPostalCode(),"postCode");
        assertEquals(payment.getTransactions().get(0).getItemList().getShippingAddress().getPhone(),"1");


        assertEquals(payment.getPayer().getPayerInfo().getFirstName(),"FirstName");
        assertEquals(payment.getPayer().getPayerInfo().getLastName(),"LastName");
        assertEquals(payment.getPayer().getPayerInfo().getEmail(),"email");

        assertEquals(payment.getPayer().getPaymentMethod(),"paypal");

        assertEquals(payment.getIntent(),"sale");

        assertThat(payment.getRedirectUrls().getReturnUrl(),stringContainsInOrder(
                "http://",getHost(),"/success"));

        assertThat(payment.getRedirectUrls().getCancelUrl(),stringContainsInOrder(
                "http://",getHost(),"/cancel","?borrowId=","1"));

    }
    @Test
    void testExecutePaymentShouldReturnPaymentWithPaymentExecution() throws PayPalRESTException, IOException {
        HttpUriRequest request = new HttpPost( "https://api.sandbox.paypal.com/v1/oauth2/token" );

        // When
        CloseableHttpResponse aexecute = HttpClientBuilder.create().build().execute(request);

        APIContext apiContext = new APIContext("2","2", "sandbox");
        Payment payment = paypalService.executePayment("1", "2");
        CloseableHttpResponse execute = HttpClientBuilder.create().build().execute(request);


    }




*/



}