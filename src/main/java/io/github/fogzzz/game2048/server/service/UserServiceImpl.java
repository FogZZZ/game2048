package io.github.fogzzz.game2048.server.service;

import io.github.fogzzz.game2048.server.dto.UserDto;
import io.github.fogzzz.game2048.server.entity.User;
import io.github.fogzzz.game2048.server.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final GameStateService gameStateService;

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
    public UserDto saveMaxScoreIfNeed(UserDto userDto) {
        User user = userRepo.getUserByName(userDto.getName());
        int currentMaxScore = gameStateService.getScore();
        if (user.getMaxScore() < currentMaxScore) {
            user.setMaxScore(currentMaxScore);
            user = userRepo.save(user);
        }
        return user.toDto();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
