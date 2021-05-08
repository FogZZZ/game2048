package io.github.fogzzz.game2048.client.service;

import io.github.fogzzz.game2048.client.dto.User;

public interface UserService {
    boolean checkUserName(String name);

    User sendCredentials(User user, boolean userExists);

    User saveMaxScore(User user);
}
