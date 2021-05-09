package io.github.fogzzz.game2048.client.service;

import io.github.fogzzz.game2048.client.dto.User;
import io.github.fogzzz.game2048.client.dto.UserForRegistration;
import io.github.fogzzz.game2048.client.errorhandling.HandleException;
import io.github.fogzzz.game2048.client.view.Tile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

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
    public User registerUser(UserForRegistration user) {
        return restTemplate.postForObject(serverUrl + "register_user", user, User.class);
    }

    @Override
    public User loginUser(User user) {
        ResponseEntity<User> response = restTemplate.exchange(serverUrl + "login_user?name=" + user.getName(), HttpMethod.POST,
                new HttpEntity<>(createHeaders(user)), User.class);
        return response.getBody();
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
        ResponseEntity<User> response = restTemplate.exchange(serverUrl + "save_max_score?name=" + user.getName(), HttpMethod.POST,
                new HttpEntity<>(createHeaders(user)), User.class);
        return response.getBody();
    }

    private HttpHeaders createHeaders(User user) {
        return new HttpHeaders() {{
            String headerValue = "Basic " + new String(user.getEncodedCredentials(), StandardCharsets.US_ASCII);
            set("Authorization", headerValue);
        }};
    }
}

