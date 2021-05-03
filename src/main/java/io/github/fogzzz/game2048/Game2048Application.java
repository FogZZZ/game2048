package io.github.fogzzz.game2048;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

/**
 * план:
 * - запустить на swing                                         Готово!
 * - перевести все в бины                                       Готово!
 * - добавить регистрацию и вход по паролю
 * - добавить сохранение результатов в бд
 * - запустить приложение в докере
 * - добавить гит
 * - сделать клиент-серверную архитектуру с http протоколом
 * - перевести фронтенд на html + css
 */
@SpringBootApplication
public class Game2048Application {

    public static void main(String[] args) {
        ApplicationContext ctx = new SpringApplicationBuilder(Game2048Application.class).headless(false).run(args);
        var game = ctx.getBean("game", Game.class);
        game.start();
    }
}
