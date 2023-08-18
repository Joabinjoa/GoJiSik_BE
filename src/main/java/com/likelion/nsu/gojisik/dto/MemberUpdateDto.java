package com.likelion.nsu.gojisik.dto;

import com.likelion.nsu.gojisik.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDto {
    private String password;
    private String username;
    private LocalDateTime birthday;

    public static MemberUpdateDto from(User user) {
        if(user == null) return null;

        return MemberUpdateDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .birthday(user.getBirthDay())
                .build();
    }


}
