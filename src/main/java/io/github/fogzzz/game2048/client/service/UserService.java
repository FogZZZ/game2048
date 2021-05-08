package io.github.fogzzz.game2048.client.service;

import io.github.fogzzz.game2048.client.dto.User;

public interface UserService {
    boolean checkUserName(String name);

    User sendCredentials(String name, String password, boolean userExists);

    User loginUser(String name, String password);

    User registerUser(String name, String password);

    User saveMaxScore(User user);
}
