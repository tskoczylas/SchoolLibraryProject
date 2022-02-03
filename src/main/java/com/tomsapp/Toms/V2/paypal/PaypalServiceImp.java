package com.tomsapp.Toms.V2.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.entity.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.*;

import static com.tomsapp.Toms.V2.utils.HostUtils.getHost;
@Component
public class PaypalServiceImp implements PaypalService {
APIContext apiContext;

    PayPalHttpClient httpClient;


public PaypalServiceImp(APIContext apiContext) {
        this.apiContext = apiContext;
    }





    static protected List<Item>  createItem(BorrowDto borrow){
    List<Item> itemLists= new ArrayList<>();

        for (Books book : borrow.getBooks()) {
        Item item = new Item();
        item.setQuantity("1");
        item.setCurrency("GBP");
        item.setName(book.getTitle());
        item.setPrice(borrow.getCountSumPricePerItemFormat());
        itemLists.add(item);
    }  return itemLists;}


    @Override
    public HttpResponse<com.paypal.orders.Order> createPaymentHttpRes(BorrowDto borrow, String successUrl, String cancelUrl) {
        return null;
    }

    @Override
    public  Payment createPayment(BorrowDto borrow, String successUrl, String cancelUrl) throws PayPalRESTException {


        //#Aamount
        Amount amount = new Amount();
        Details details = new Details();
        details.setSubtotal(borrow.getTotalCostFormat());
        details.setShipping("0");
        amount.setCurrency("GBP");
        amount.setDetails(details);
        amount.setTotal(borrow.getTotalCostFormat());

        //#ShipingAdress
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setLine1(borrow.getStudent().getAdresses().getAddressFirstLine());
        shippingAddress.setLine2(borrow.getStudent().getAdresses().getAddressSecondLine());
        shippingAddress.setPostalCode(borrow.getStudent().getAdresses().getPostCode());
        shippingAddress.setCountryCode("GB");
        shippingAddress.setCity(borrow.getStudent().getAdresses().getAddressSecondLine());
        shippingAddress.setPhone(borrow.getStudent().getAdresses().getTelephone());

        //#IteamList
        ItemList itemList = new ItemList();
        itemList.setItems(createItem(borrow));
        itemList.setShippingAddress(shippingAddress);

        //#Transaction
        Transaction transaction = new Transaction();
        transaction.setItemList(itemList);
        transaction.setAmount(amount);
        List<Transaction> transactions = Collections.singletonList(transaction);

        //#PayerInfo
        Payer payer = new Payer();
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName(borrow.getStudent().getFirstName());
        payerInfo.setEmail(borrow.getStudent().getEmail());
        payerInfo.setLastName(borrow.getStudent().getLastName());

        //#Payer
        payer.setPayerInfo(payerInfo);
        payer.setPaymentMethod("paypal");

        //#Payment
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);




        //#RedirectUrl
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://" +getHost() + cancelUrl + "?borrowId="+borrow.getId());
        redirectUrls.setReturnUrl( "http://"+getHost() +successUrl);

        payment.setRedirectUrls(redirectUrls);


        return payment.create(apiContext);

    }

    @Override
    public  Payment  executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution= new PaymentExecution();
       paymentExecution.setPayerId(payerId);
      return payment.execute(apiContext,paymentExecution);

    }


}
