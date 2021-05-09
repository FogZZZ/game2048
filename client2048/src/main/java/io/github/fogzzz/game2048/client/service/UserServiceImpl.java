package io.github.fogzzz.game2048.client.service;

import io.github.fogzzz.game2048.client.dto.User;
import io.github.fogzzz.game2048.client.dto.UserForRegistration;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service("userClientService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RestService restService;

    @Override
    public boolean checkUserName(String name) {
        return restService.checkUserName(name);
    }

    @Override
    public User sendCredentials(User user, String password, boolean userExists) {
        user.setEncodedCredentials(encodeCredentials(user.getName(), password));
        if (userExists) {
            return restService.loginUser(user);
        } else {
            return restService.registerUser(new UserForRegistration(user.getName(), password));
        }
    }

    private byte[] encodeCredentials(String name, String password) {
        String credentials = name + ":" + password;
        return Base64.encodeBase64(credentials.getBytes(StandardCharsets.US_ASCII));
    }

    @Override
    public User saveMaxScore(User user) {
        return restService.saveMaxScore(user);
    }
}
