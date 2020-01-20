package com.yjs.console;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ConsoleApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConsoleApplication.class)
                .properties("spring.config.name=application").run(args);
    }

}
