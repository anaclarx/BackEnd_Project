package com.emse.spring.faircop;

import com.emse.spring.faircop.hello.ConsoleGreetingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FaircopApplicationConfig {

    @Bean
    public CommandLineRunner greetingCommandLine(ConsoleGreetingService consoleGreetingService) { // (3)
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                consoleGreetingService.greet("Spring");


            }
        };
    }
}
