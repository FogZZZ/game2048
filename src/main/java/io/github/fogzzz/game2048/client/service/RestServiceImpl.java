package io.github.fogzzz.game2048.client.service;

import io.github.fogzzz.game2048.client.errorhandling.HandleException;
import io.github.fogzzz.game2048.client.view.Tile;
import io.github.fogzzz.game2048.client.dto.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static java.lang.String.format;

@Getter
@Setter
@Component
@HandleException
@RequiredArgsConstructor
public class RestServiceImpl implements RestService {

    private final RestTemplate restTemplate;

    @Value("${server.address}")
    private String serverAddress;
    @Value("${server.port}")
    private String serverPort;
    private String serverUrl;

    @PostConstruct
    public void init() {
        serverUrl = format("http://%s:%s/", serverAddress, serverPort);
    }

    @Override
    public boolean checkUserName(String name) {
        return restTemplate.getForObject(serverUrl + "check_user_name?name=" + name, Boolean.class);
    }

    @Override
    public User registerUser(User user) {
        return restTemplate.postForObject(serverUrl + "register_user", user, User.class);
    }

    @Override
    public User loginUser(User user) {
        return restTemplate.postForObject(serverUrl + "login_user", user, User.class);
    }

    @Override
    public Tile[][] getGameTiles() {
        return restTemplate.getForObject(serverUrl + "game_tiles", Tile[][].class);
    }

    @Override
    public int getScore() {
        return restTemplate.getForObject(serverUrl + "score", Integer.class);
    }

    @Override
    public int getMaxTile() {
        return restTemplate.getForObject(serverUrl + "max_tile", Integer.class);
    }

    @Override
    public void resetGame() {
        restTemplate.postForEntity(serverUrl + "reset_game", null, String.class);
    }

    @Override
    public boolean canMove() {
        return restTemplate.getForObject(serverUrl + "can_move", Boolean.class);
    }

    @Override
    public void move(String direction) {
        restTemplate.postForEntity(serverUrl + "move?direction=" + direction, null, String.class);
    }

    @Override
    public void rollback() {
        restTemplate.postForEntity(serverUrl + "rollback", null, String.class);
    }

    @Override
    public void randomMove() {
        restTemplate.postForEntity(serverUrl + "random_move", null, String.class);
    }

    @Override
    public void autoMove() {
         restTemplate.postForEntity(serverUrl + "auto_move", null, String.class);
    }

    @Override
    public User saveMaxScore(User user) {
        return restTemplate.postForObject(serverUrl + "save_max_score", user, User.class);
    }
}

