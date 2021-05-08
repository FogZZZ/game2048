package io.github.fogzzz.game2048.server.service;

import io.github.fogzzz.game2048.server.dto.UserDto;
import io.github.fogzzz.game2048.server.entity.User;
import io.github.fogzzz.game2048.server.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public Boolean checkUserName(String name) {
        return userRepo.existsUserByName(name);
    }

    @Override
    public UserDto registerUser(UserDto user) {
        User entity = userRepo.save(user.toEntity());
        return entity.toDto();
    }

    @Override
    public ResponseEntity<UserDto> loginUser(UserDto userDto) {
        User entity = userRepo.getUserByName(userDto.getName());
        if (entity.getPassword().equals(userDto.getPassword())) {
            return ResponseEntity.ok(entity.toDto());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    public void saveMaxScoreIfNeed(UserDto userDto) {
        User user = userRepo.getUserByName(userDto.getName());
        if (user.getMaxScore() < userDto.getMaxScore()) {
            user.setMaxScore(userDto.getMaxScore());
            userRepo.save(user);
        }
    }
}
