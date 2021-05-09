package io.github.fogzzz.game2048.server.controller;

import io.github.fogzzz.game2048.server.dto.UserDto;
import io.github.fogzzz.game2048.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("check_user_name")
    public Boolean checkUserName(@RequestParam String name) {
        return userService.checkUserName(name);
    }

    @PostMapping("register_user")
    public UserDto registerUser(@RequestBody UserDto user) {
        return userService.registerUser(user);
    }

    @PostMapping("login_user")
    public UserDto loginUser(@RequestParam String name) {
        return userService.getUserByName(name);
    }

    @PostMapping("save_max_score")
    public UserDto saveMaxScore(@RequestParam String name) {
        return userService.saveMaxScoreIfNeed(name);
    }
}
