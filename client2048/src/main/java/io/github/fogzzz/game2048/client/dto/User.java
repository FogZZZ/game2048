package io.github.fogzzz.game2048.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @JsonProperty(required = true)
    private String name;
    @JsonProperty
    private Integer maxScore;

    private byte[] encodedCredentials;

    public User(String name) {
        this.name = name;
    }
}
