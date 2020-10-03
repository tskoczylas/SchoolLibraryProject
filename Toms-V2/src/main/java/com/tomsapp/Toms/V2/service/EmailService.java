package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.entity.Token;

public interface EmailService {
    void sendConformationMessageLogin(Token confirmationToken);



    void sendConformationMessageNewOrder(Borrow borrow);

  void   sendConformationMessageNewOrder4(Borrow borrow);
    String getHost();
}
