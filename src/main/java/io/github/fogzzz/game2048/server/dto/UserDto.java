package io.github.fogzzz.game2048.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.fogzzz.game2048.server.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    @JsonProperty(required = true)
    private String name;
    @JsonProperty(access = WRITE_ONLY)
    private String password;
    @JsonProperty
    private Integer maxScore;

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .password(this.password)
                .maxScore(this.maxScore)
                .build();
    }
}
