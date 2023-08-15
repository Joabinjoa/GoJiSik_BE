package com.likelion.nsu.gojisik.domain;



import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;


    private String phonenum;

    private String password;

    private String username;

    private int font;
    private LocalDateTime birthday;

    private Grade grade;

    private Long point;

    @OneToMany(mappedBy = "member")
    private List<Question> questionList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Answer> answerList = new ArrayList<>();

    private boolean activated;
    public Member(String subject, String s, Collection<? extends GrantedAuthority> authorities) {

    }

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    public Member() {

    }

    public LocalDateTime getBirthDay() {
        return this.birthday;
    }
}
