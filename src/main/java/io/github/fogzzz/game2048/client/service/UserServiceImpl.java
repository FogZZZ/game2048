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
    public User sendCredentials(User user, boolean userExists) {
        if (userExists) {
            return loginUser(user);
        } else {
            return registerUser(user);
        }
    }

    private User loginUser(User user) {
        return restService.loginUser(user);
    }

    private User registerUser(User user) {
        return restService.registerUser(user);
    }

    @Override
    public User saveMaxScore(User user) {
        return restService.saveMaxScore(user);
    }
}
