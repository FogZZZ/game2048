package io.github.fogzzz.game2048.server.entity;

import io.github.fogzzz.game2048.server.dto.UserDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;
    @Column
    private String password;
    @Column(name = "max_score", insertable = false)
    private Integer maxScore;

    public UserDto toDto() {
        return UserDto.builder()
                .name(this.name)
                .maxScore(this.maxScore)
                .build();
    }
}
