package ru.agapov.springintegrationproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;


@Configuration
public class IntegrationConfig {

    @Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel errorChannel() {
        return new DirectChannel();
    }

    @Bean
    public CustomSplitter customSplitter() {
        return new CustomSplitter();
    }

    @Bean
    public IntegrationFlow processFlow() {
        return IntegrationFlow.from(inputChannel())
                .split(customSplitter()) // Используем кастомный сплиттер для добавления хедеров
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
