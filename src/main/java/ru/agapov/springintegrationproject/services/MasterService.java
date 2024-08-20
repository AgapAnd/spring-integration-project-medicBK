package ru.agapov.springintegrationproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.agapov.springintegrationproject.utils.UtilsForArrays;

import java.util.Arrays;

@Service
public class MasterService {
    @Autowired
    private MessageChannel inputChannel;
    @Autowired
    private MessageChannel errorChannel;

    public void processStrings(String[] input) {
        if (!UtilsForArrays.arrayNotNull(input)) {
            this.errorChannel.send(MessageBuilder.withPayload("Input array is null!").build());
        } else if (!UtilsForArrays.arrayNotEmpty(input)) {
            this.errorChannel.send(MessageBuilder.withPayload("Input array is empty").build());
        } else {
            System.out.println("\nInput array: " + Arrays.toString(input) + "\n");
            this.inputChannel.send(MessageBuilder.withPayload(input).build());
        }
    }
}
