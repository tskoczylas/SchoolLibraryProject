package com.tomsapp.Toms.V2.paypal;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import com.paypal.payments.AuthorizationsCaptureRequest;
import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.entity.Books;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Primary
public class PaypalServiceV2Impl implements PaypalService{

    @Value("${value.paypal.clientId}")
    String clientId;
    @Value("${value.paypal.clientSecret}")
    String clientSecret;

    PayPalHttpClient httpClient;



    public PaypalServiceV2Impl(PayPalHttpClient httpClient) {
        this.httpClient = httpClient;
    }



    @Override
    public HttpResponse<Order> createPaymentHttpRes(BorrowDto borrow, String successUrl, String cancelUrl)  {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.header("prefer","return=representation");
        request.requestBody(buildMinimumRequestBody(borrow,successUrl,cancelUrl +  "?borrowId="+borrow.getId()));

        HttpResponse<Order> response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (true) {
            if (response.statusCode() == 201) {
                System.out.println("Order with Complete Payload: ");
                System.out.println("Status Code: " + response.statusCode());
                System.out.println("Status: " + response.result().status());
                System.out.println("Order ID: " + response.result().id());
                System.out.println("Intent: " + response.result().checkoutPaymentIntent());
                System.out.println("Links: ");
                for (LinkDescription link : response.result().links()) {
                    System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
                }
                System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode()
                        + " " + response.result().purchaseUnits().get(0).amountWithBreakdown().value());
                System.out.println("Full response body:");
                //System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
            }


        }

        return response;
    }

    @Override
    public Payment createPayment(BorrowDto borrow, String successUrl, String cancelUrl) throws PayPalRESTException {
        return null;
    }

    private OrderRequest buildMinimumRequestBody(BorrowDto borrow,String successUrl, String cancelUrl) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("AUTHORIZE");
        ApplicationContext applicationContext = new ApplicationContext()
                .cancelUrl(cancelUrl).returnUrl(successUrl);
        orderRequest.applicationContext(applicationContext);
        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        Money money = new Money();
        money.currencyCode("GBP");
        money.value(borrow.getTotalCostFormat());
        AmountBreakdown amountBreakdown = new AmountBreakdown();
        amountBreakdown.itemTotal(money);
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .items(createItem(borrow))
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("GBP").value(borrow.getTotalCostFormat()).amountBreakdown(amountBreakdown));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

    static protected List<Item>  createItem(BorrowDto borrow){
        List<Item> itemLists= new ArrayList<>();

        for (Books book : borrow.getBooks()) {
            Money money = new Money();
            money.currencyCode("GBP");
            money.value(borrow.getCountSumPricePerItemFormat());

            Item item = new Item();
            item.quantity("1");
            item.unitAmount(money);
            item.name(book.getTitle());
            itemLists.add(item);
        }  return itemLists;}



    public HttpResponse<Order> createOrder(boolean debug) throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.header("prefer","return=representation");
      //  request.requestBody(buildMinimumRequestBody(borrow));

        HttpResponse<Order> response = httpClient.execute(request);
        if (debug) {
            if (response.statusCode() == 201) {

                System.out.println("Authorization Ids:");
                response.result().purchaseUnits().forEach(purchaseUnit -> purchaseUnit.payments().authorizations().stream()
                        .map(Authorization::id).forEach(System.out::println));

                System.out.println("Order with Complete Payload: ");
                System.out.println("Status Code: " + response.statusCode());
                System.out.println("Status: " + response.result().status());
                System.out.println("Order ID: " + response.result().id());
                System.out.println("Intent: " + response.result().checkoutPaymentIntent());
                System.out.println("Links: ");
                for (LinkDescription link : response.result().links()) {
                    System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
                }
                System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode()
                        + " " + response.result().purchaseUnits().get(0).amountWithBreakdown().value());
                System.out.println("Full response body:");
                //System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
            }
        }
        System.out.println("authorization " +  response.result().purchaseUnits().get(0).payments().authorizations().get(0).id());

        return response;
    }

    public HttpResponse<com.paypal.payments.Capture> captureOrder(String authId, boolean debug) throws IOException {
        AuthorizationsCaptureRequest request = new AuthorizationsCaptureRequest(authId);
        request.requestBody(new OrderRequest());
        HttpResponse<com.paypal.payments.Capture> response = httpClient.execute(request);
        if (debug) {

            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Status: " + response.result().status());
            System.out.println("Capture ID: " + response.result().id());
            System.out.println("Links: ");
            for (com.paypal.payments.LinkDescription link : response.result().links()) {
                System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
            }
            System.out.println("Full response body:");
        }
        return response;
    }

    @Override
    public Payment executePayment(String paymentId, String payerId)  {
        try {
            captureOrder(paymentId,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
