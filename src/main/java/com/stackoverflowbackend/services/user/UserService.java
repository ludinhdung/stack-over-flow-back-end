package com.stackoverflowbackend.services.user;

import com.stackoverflowbackend.dtos.UserDto;
import com.stackoverflowbackend.exceptions.ObjectNotFoundException;
import com.stackoverflowbackend.mappers.UserMapper;
import com.stackoverflowbackend.models.User;
import com.stackoverflowbackend.repositories.UserRepository;
import com.stackoverflowbackend.security.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(UserPrinciple::new)
                .orElseThrow(() -> new UsernameNotFoundException("username" + username + "is not found"));
    }


    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }


    public UserDto findById(Long userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("user", userId));

        return userMapper.toDto(foundUser);
    }


    public UserDto save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }


    public UserDto update(Long userId, User user) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("user", userId));

        userMapper.update(foundUser, user);
        return userMapper.toDto(userRepository.save(foundUser));
    }


    public void delete(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("user", userId));

        userRepository.deleteById(userId);
    }

}
