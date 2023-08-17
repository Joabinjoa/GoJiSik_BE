package com.likelion.nsu.gojisik.domain;



import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

public interface UserDetails extends Serializable {

    Collection<? extends GrantedAuthority> getAuthorities();

    int getFont();
    String getPassword();

    String getUsername();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    boolean isAccountNonExpired();
    String getPhonenum();
    LocalDateTime getBirthDay();
    Long getId();

    boolean isAccountNonExpried();
    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();
    boolean isEnabled();
}
