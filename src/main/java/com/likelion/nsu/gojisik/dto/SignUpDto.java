package com.likelion.nsu.gojisik.dto;

import com.likelion.nsu.gojisik.domain.Member;
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

    public static SignUpDto from(Member member) {
        if(member == null) return null;

        return SignUpDto.builder()
                .phonenum(member.getPhonenum())
                .username(member.getUsername())
                .password(member.getPassword())
                .font(member.getFont())
                .birthday(member.getBirthDay())
                .authorityDtoSet(member.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthority()).build())
                        .collect(Collectors.toSet()))
                .build();
    }


}
