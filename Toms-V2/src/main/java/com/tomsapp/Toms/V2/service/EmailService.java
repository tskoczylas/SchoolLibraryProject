package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Token;

public interface EmailService {
    void sendConformationMessage(Token confirmationToken);

    String getHost();
}
