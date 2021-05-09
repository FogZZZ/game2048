package io.github.fogzzz.game2048.server.repository;

import io.github.fogzzz.game2048.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    Boolean existsUserByName(String name);
    User getUserByName(String name);
}
