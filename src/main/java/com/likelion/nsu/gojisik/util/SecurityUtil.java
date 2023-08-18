package com.likelion.nsu.gojisik.util;


import com.likelion.nsu.gojisik.domain.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Optional;

public class SecurityUtil {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil() {}

    public static Optional<String> getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication : {} " , authentication.getName());

        if (authentication == null) {
            logger.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String phonenum = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            logger.info("springSecurityUser : {}" ,springSecurityUser);
            phonenum = springSecurityUser.getPhonenum();
        } else if (authentication.getPrincipal() instanceof String) {
            phonenum = (String) authentication.getPrincipal();
        }

        logger.info("phonenum:{}",phonenum);
        return Optional.ofNullable(phonenum);
    }
}
