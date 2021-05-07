package io.github.fogzzz.game2048.server.controller;

import io.github.fogzzz.game2048.server.dto.UserDto;
import io.github.fogzzz.game2048.server.entity.User;
import io.github.fogzzz.game2048.server.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LoginController {

    private final UserRepo userRepo;

    @GetMapping("check_user_name")
    public Boolean checkUserName(@Param("name") String name) {
        return userRepo.existsUserByName(name);
    }

    @PostMapping("register_user")
    public UserDto registerUser(@RequestBody UserDto user) {
        User entity = userRepo.save(user.toEntity());
        return entity.toDto();
    }

    @PostMapping("login_user")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto) {
        User entity = userRepo.getUserByName(userDto.getName());
        if (entity.getPassword().equals(userDto.getPassword())) {
            return ResponseEntity.ok(entity.toDto());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
