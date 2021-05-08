package io.github.fogzzz.game2048.client.service;

import io.github.fogzzz.game2048.client.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("userClientService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RestService restService;

    @Override
    public boolean checkUserName(String name) {
        return restService.checkUserName(name);
    }

    @Override
    public User sendCredentials(String name, String password, boolean userExists) {
        if (userExists) {
            return loginUser(name, password);
        } else {
            return registerUser(name, password);
        }
    }

    @Override
    public User loginUser(String name, String password) {
        return restService.loginUser(new User(name, password));
    }

    @Override
    public User registerUser(String name, String password) {
        return restService.registerUser(new User(name, password));
    }
}
