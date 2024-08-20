package ru.agapov.springintegrationproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.agapov.springintegrationproject.services.MasterService;

@SpringBootApplication
public class SpringIntegrationProjectApplication {
    public static final String[] INPUT_ARRAY = new String[]{"Item1", "Item2", "Item3", "Item4", "Item5", "Item6"};
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringIntegrationProjectApplication.class, args);

        ctx.getBean(MasterService.class).processStrings(INPUT_ARRAY);

        ctx.close();

    }

}
