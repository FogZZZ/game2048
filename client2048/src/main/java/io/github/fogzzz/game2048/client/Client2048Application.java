package io.github.fogzzz.game2048.client;

import io.github.fogzzz.game2048.client.view.Game;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Client2048Application {

    public static void main(String[] args) {
        ApplicationContext ctx = new SpringApplicationBuilder(Client2048Application.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .run(args);
        var game = ctx.getBean("game", Game.class);
        game.start();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
