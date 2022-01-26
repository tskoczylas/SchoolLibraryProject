package com.tomsapp.Toms.V2.paypal;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalContex {

    @Value("${value.paypal.clientId}")
    String clientId;
    @Value("${value.paypal.clientSecret}")
    String clientSecret;

    @Bean()
    APIContext createAPIContext() {

        return new APIContext(clientId, clientSecret, "sandbox");
    }

}
