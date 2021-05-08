package io.github.fogzzz.game2048.server.controller;

import io.github.fogzzz.game2048.server.dto.UserDto;
import io.github.fogzzz.game2048.server.repository.UserRepo;
import io.github.fogzzz.game2048.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserRepo userRepo;
    private final UserService userService;

    @GetMapping("check_user_name")
    public Boolean checkUserName(@RequestParam("name") String name) {
        return userService.checkUserName(name);
    }

    @PostMapping("register_user")
    public UserDto registerUser(@RequestBody UserDto user) {
        return userService.registerUser(user);
    }

    @PostMapping("login_user")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto) {
        return userService.loginUser(userDto);
    }

    @PostMapping("save_max_score")
    public UserDto saveMaxScore(@RequestBody UserDto userDto) {
        return userService.saveMaxScoreIfNeed(userDto);
    }
}
