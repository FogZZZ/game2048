package io.github.fogzzz.game2048.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserForRegistration {
    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true)
    private String password;

    public UserForRegistration(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
