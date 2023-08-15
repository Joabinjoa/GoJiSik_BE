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
public class MemberUpdateDto {

    private String phoneNumber;
    private String password;
    private String username;
    private LocalDateTime birthday;
    private Long id;
    private int font;
    private Set<AuthorityDto> authorityDtoSet;

    public SignUpDto(String name, String uid, String password, String phoneNumber, LocalDateTime birthday,Long id , int font){
        this.username =name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.id = id;
        this.font = font;
    }
    public static SignUpDto from(Member member) {
        if(member == null) return null;

        return SignUpDto.builder()
                .phoneNumber(member.getPhoneNumber())
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
