package com.emse.spring.faircop.hello;

import com.emse.spring.faircop.hello.GreetingService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class) // (1)
class GreetingServiceTests {

    @Test
    public void testGreeting(CapturedOutput output) {
        GreetingService greetingService = new ConsoleGreetingService(); // (2)
        greetingService.greet("Spring");
        Assertions.assertThat(output.getAll()).contains("Hello, Spring!");
    }
}