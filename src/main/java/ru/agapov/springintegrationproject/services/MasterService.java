package ru.agapov.springintegrationproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MasterService {
    @Autowired
    private MessageChannel inputChannel;
    @Autowired
    private MessageChannel errorChannel;

    public void processStrings(String[] input) throws Exception {
        if (arrayNotNull(input)) {
            if (arrayNotEmpty(input)) {
                System.out.println("\nInput array: " + Arrays.toString(input) + "\n");
                this.inputChannel.send(MessageBuilder.withPayload(input).build());
            } else {
                this.errorChannel.send(MessageBuilder.withPayload("Input array is empty!").build());
            }
        } else {
            this.errorChannel.send(MessageBuilder.withPayload("Input array is null").build());
        }
    }

    public boolean arrayNotNull(String[] array) {
        return (array != null) ? true : false;
    }
    public boolean arrayNotEmpty(String[] array) {
        return (array.length > 0) ? true : false;
    }
}
