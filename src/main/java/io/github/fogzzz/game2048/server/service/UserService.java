package io.github.fogzzz.game2048.server.service;

import io.github.fogzzz.game2048.server.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    Boolean checkUserName(String name);
    UserDto registerUser(UserDto user);
    ResponseEntity<UserDto> loginUser(UserDto userDto);
    UserDto saveMaxScoreIfNeed(UserDto userDto);
}
