package com.tomsapp.Toms.V2.paypal;

import com.paypal.base.rest.APIContext;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
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

        @Bean
    public PayPalHttpClient clientCreateAPIContext() {
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, clientSecret);

        PayPalHttpClient client = new PayPalHttpClient(environment);
        System.out.println(environment.clientId());
        return client;
    }


}
