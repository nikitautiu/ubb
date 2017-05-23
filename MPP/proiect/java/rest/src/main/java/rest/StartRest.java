package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("rest")
@SpringBootApplication
public class StartRest {
    public static void main(String[] args) {

        SpringApplication.run(StartRest.class, args);
    }
}

