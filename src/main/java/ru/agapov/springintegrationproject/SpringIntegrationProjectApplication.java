package ru.agapov.springintegrationproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.DirectChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import ru.agapov.springintegrationproject.services.MasterService;

@SpringBootApplication
public class SpringIntegrationProjectApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringIntegrationProjectApplication.class, args);

        String[] input = new String[]{"Item1", "Item2", "Item3", "Item4", "Item5", "Item6"};

//        String[] input1 = new String[]{};
//
//        String[] input2 = null;

        ctx.getBean(MasterService.class).processStrings(input);

        ctx.close();

    }

}
