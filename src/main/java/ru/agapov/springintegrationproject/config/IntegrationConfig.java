package ru.agapov.springintegrationproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.DirectChannelSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;


@Configuration
public class IntegrationConfig {

    @Bean
    public DirectChannelSpec inputChannel() {
        return MessageChannels.direct();
    }

    @Bean
    public DirectChannelSpec outputChannel() {
        return MessageChannels.direct();
    }

    @Bean
    public DirectChannelSpec errorChannel() {
        return MessageChannels.direct();
    }

    @Bean
    public IntegrationFlow processFlow() {
        return IntegrationFlow.from(inputChannel())
                .split(new CustomSplitter()) // Используем кастомный сплиттер для добавления хедеров
                .route("headers.type",
                        mapping -> mapping
                                .subFlowMapping("toUpperCaseString", sf -> sf.handle("slave2service","toUpperCaseString"))
                                .subFlowMapping("reverseString", sf -> sf.handle("slave1service", "reverseString"))
                        )
                .aggregate()
                .channel(outputChannel())
                .get();
    }

    @Bean
    public IntegrationFlow errorFlow() { // Обработка ошибок из errorChannel
        return IntegrationFlow.from(errorChannel())
                .handle(message -> {
                    System.err.println("Error occurred: " + message.getPayload());
                })
                .get();
    }


    public static class CustomSplitter extends AbstractMessageSplitter {
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

    @Bean
    public IntegrationFlow resultFlow() {
        return IntegrationFlow.from(outputChannel())
                .handle(result -> {
//                     Обработчик результирующего сообщения
                    System.out.println("\nResult array: " + result.getPayload() + "\n");
                })
                .get();
    }
}
