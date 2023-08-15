package com.likelion.nsu.gojisik.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authority")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authroity_id;
    @Column(name = "authority_name", length = 50)
    private String authorityName;

    public String getAuthority() {
        return authorityName;
    }
}
