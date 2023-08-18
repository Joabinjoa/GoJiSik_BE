package com.likelion.nsu.gojisik.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

public interface UserDetails extends Serializable {
    Long getId();

    String getPhonenum();

    String getPassword();

    String getUsername();

    int getFont();

    LocalDateTime getBirthDay();

    Collection<? extends GrantedAuthority> getAuthorities();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    boolean isAccountNonExpired();

    boolean isAccountNonExpried();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();

    boolean isEnabled();
}
