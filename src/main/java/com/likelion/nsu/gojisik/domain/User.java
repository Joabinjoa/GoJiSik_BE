package com.likelion.nsu.gojisik.domain;



import io.jsonwebtoken.Claims;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;


    private String phonenum;

    private String password;

    private String username;

    private int font;
    private LocalDateTime birthday;

    private Grade grade;

    private Long point;

    @OneToMany(mappedBy = "user")
    private List<Question> questionList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Answer> answerList = new ArrayList<>();

    private boolean activated;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    public User() {

    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phonenum=" + phonenum +
                // 다른 멤버 변수들을 포함시키거나, 원하는 정보를 포함시키도록 수정
                '}';
    }

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities.stream()
                .filter(authority -> authority instanceof Authority)
                .map(authority -> (Authority) authority)
                .collect(Collectors.toSet());
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    public LocalDateTime getBirthDay() {
        return this.birthday;
    }

    @Override
    public boolean isAccountNonExpried() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한 정보를 가져와서 GrantedAuthority 객체로 변환하여 반환
        // 예시: 권한 정보를 가져와서 SimpleGrantedAuthority로 변환하여 리스트에 담아 반환
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 권한 정보를 가져와서 authorities 리스트에 추가
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }


}
