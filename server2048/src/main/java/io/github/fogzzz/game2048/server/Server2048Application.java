package io.github.fogzzz.game2048.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * план:
 * - запустить на swing                                         Готово!
 * - перевести все в бины                                       Готово!
 * - добавить регистрацию и вход по паролю                      Готово!
 * - добавить сохранение результатов в бд                       Готово!
 * - запустить приложение в докере
 * - добавить гит                                               Готово!
 * - сделать клиент-серверную архитектуру с http протоколом     Готово!
 * - сделать сервер многопоточным
 * - перевести фронтенд на html + css
 */
@SpringBootApplication
public class Server2048Application {

    public static void main(String[] args) {
        SpringApplication.run(Server2048Application.class, args);
    }
}
