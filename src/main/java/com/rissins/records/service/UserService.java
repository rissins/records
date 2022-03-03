package com.rissins.records.service;

import com.rissins.records.domain.constant.Status;
import com.rissins.records.domain.User;
import com.rissins.records.dto.UserResponse;
import com.rissins.records.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(UserResponse userResponse) {
        String encodePassword = passwordEncoder.encode(userResponse.getUserPassword());
        User user = User.builder()
                .userId(userResponse.getUserId())
                .userPassword(encodePassword)
                .build();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAllByUser() {
        return userRepository.findAll();
    }

    public Status login(User user) {
        User byUserId = findByUserId(user.getUserId());
        boolean matches = passwordEncoder.matches(user.getUserPassword(), byUserId.getUserPassword());
        return matches ? Status.ACCEPTED : Status.DENIED;
    }

    @Transactional(readOnly = true)
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public Status overlapCheckByUserId(String userId) {

        try {
            String userId1 = userRepository.findByUserId(userId).getUserId();
            return Status.DENIED;
        } catch (NullPointerException e) {
            return Status.ACCEPTED;
        }
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
