package ru.agapov.springintegrationproject.config;

import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class CustomSplitter extends AbstractMessageSplitter {
    private int index = 0;

    @Override
    protected Object splitMessage(Message<?> message) {
        String[] payload = (String[]) message.getPayload();
        Object[] result = new Object[payload.length];

        for (String item : payload) {
            if (index % 2 !=0) {
                result[index] = MessageBuilder.withPayload(item)
                        .setHeader("type", "toUpperCaseString")
                        .build();
            } else {
                result[index] = MessageBuilder.withPayload(item)
                        .setHeader("type", "reverseString")
                        .build();
            }
            index++;
        }
        return result;
    }
}
