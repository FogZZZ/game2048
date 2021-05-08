package io.github.fogzzz.game2048.server.service;

import io.github.fogzzz.game2048.server.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Boolean checkUserName(String name);
    UserDto registerUser(UserDto user);
    UserDto getUserByName(String name);
    UserDto saveMaxScoreIfNeed(String name);
}
