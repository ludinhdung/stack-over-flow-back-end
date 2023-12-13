package com.stackoverflowbackend.security;

import com.stackoverflowbackend.dtos.UserDto;
import com.stackoverflowbackend.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTProvider jwtProvider;

    private final UserMapper userMapper;

    public Map<String, Object> createLoginInfo(Authentication authentication) {

        UserPrinciple principle = (UserPrinciple) authentication.getPrincipal();

        UserDto userDto = userMapper.toDto(principle.getUser());

        String token = jwtProvider.createToken(authentication);

        Map<String, Object> loginResultMap = new HashMap<>();
        loginResultMap.put("userInfo", userDto);
        loginResultMap.put("token", token);

        return loginResultMap;
    }
}
