package com.likelion.nsu.gojisik.dto;

import com.likelion.nsu.gojisik.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class SignUpDto {

    private String phonenum;
    private String password;
    private String username;
    private LocalDateTime birthday;

    private Long id;
    private int font;
    private Set<AuthorityDto> authorityDtoSet;

    public static SignUpDto from(User user) {
        if(user == null) return null;

        return SignUpDto.builder()
                .phonenum(user.getPhonenum())
                .username(user.getUsername())
                .password(user.getPassword())
                .font(user.getFont())
                .birthday(user.getBirthDay())
                .id(user.getId())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthority()).build())
                        .collect(Collectors.toSet()))
                .build();
    }

    public static SignUpDto Infofrom(User user) {
        if(user == null) return null;

        return SignUpDto.builder()
                .phonenum(user.getPhonenum())
                .username(user.getUsername())
                .birthday(user.getBirthDay())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthority()).build())
                        .collect(Collectors.toSet()))
                .build();
    }

}
