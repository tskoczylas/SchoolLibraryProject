package com.tomsapp.Toms.V2.paypal;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import com.tomsapp.Toms.V2.dto.BorrowDto;

public interface PaypalService {
    HttpResponse<Order> createPaymentHttpRes(BorrowDto borrow, String successUrl, String cancelUrl);
    Payment createPayment (BorrowDto borrow, String successUrl, String cancelUrl) throws PayPalRESTException;
    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
}
