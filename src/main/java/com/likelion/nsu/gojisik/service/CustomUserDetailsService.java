package com.likelion.nsu.gojisik.service;


import com.likelion.nsu.gojisik.domain.User;
import com.likelion.nsu.gojisik.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String phonenum) throws UsernameNotFoundException {
        return userRepository.findOneWithAuthoritiesByPhonenum(phonenum)
                .map(user -> createUser(phonenum, user))
                .orElseThrow(() -> new UsernameNotFoundException(phonenum + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String phonenum, User user) {
        if (!user.isActivated()) {
            throw new RuntimeException(phonenum + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getPhonenum(),
                user.getPassword(),
                grantedAuthorities);
    }
}
