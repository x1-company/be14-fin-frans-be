package com.x1.frans.security;

import com.x1.frans.redis.service.RedisService;
import com.x1.frans.user.command.service.UserCommandService;
import com.x1.frans.user.query.service.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserQueryService userQueryService;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final UserCommandService userCommandService;

    @Autowired
    public JwtAuthenticationProvider(UserQueryService userQueryService,
                                     PasswordEncoder passwordEncoder,
                                     RedisService redisService,
                                     UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.userCommandService = userCommandService;
    }

    private static final String WRONG_PASSWORD_PREFIX = "WP";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userCode = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userQueryService.loadUserByUsername(userCode);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {

            Integer count = handleWrongPassword(userCode);

            String message = count == 5 ? "10분 내에 비밀번호를 5회 틀렸습니다. 계정이 잠금 처리 됩니다."
                    : "비밀번호가 올바르지 않습니다. (" + count + "/5) 10분 내에 5회 이상 불일치 시 계정이 잠금 처리 됩니다.";

            throw new BadCredentialsException(message);
        }

        redisService.remove(WRONG_PASSWORD_PREFIX, userCode);

        return new UsernamePasswordAuthenticationToken(userDetails,
                null,
                userDetails.getAuthorities());
    }

    private Integer handleWrongPassword(String userCode) {

        final long TEN_MINUTES_IN_MILLIS = 10 * 60 * 1000; // 600,000 ms

        int count = getWrongPasswordCount(userCode) + 1;

        if (count == 1) {
            redisService.save(WRONG_PASSWORD_PREFIX, userCode, "1", TEN_MINUTES_IN_MILLIS);
        } else {
            if (count >= 5) {
                userCommandService.accountLock(userCode);

                redisService.remove(WRONG_PASSWORD_PREFIX, userCode);
            } else {
                redisService.incrementCount(WRONG_PASSWORD_PREFIX, userCode);
            }
        }

        return count;
    }

    public int getWrongPasswordCount(String userCode) {
        if (!redisService.exists(WRONG_PASSWORD_PREFIX, userCode)) {
            return 0;
        }
        return Integer.parseInt(redisService.get(WRONG_PASSWORD_PREFIX, userCode));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
