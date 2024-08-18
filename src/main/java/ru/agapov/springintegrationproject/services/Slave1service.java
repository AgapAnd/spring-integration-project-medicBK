package ru.agapov.springintegrationproject.services;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Slave1service {
    @ServiceActivator
    public String reverseString(@Payload String input) throws InterruptedException {
        Thread.sleep((long) (Math.random()*5000 + 5000));
        return new StringBuilder(input).reverse().toString();
    }
}
