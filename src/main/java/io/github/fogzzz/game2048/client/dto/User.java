package io.github.fogzzz.game2048.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User {
    @JsonProperty(required = true)
    private String name;
    @JsonProperty
    private String password;
    @JsonProperty
    private Integer maxScore;

    public User(String name) {
        this.name = name;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
