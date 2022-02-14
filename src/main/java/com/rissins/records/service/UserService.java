package com.rissins.records.service;

import com.rissins.records.domain.User;
import com.rissins.records.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAllByUser() {
        return userRepository.findAll();
    }
}
