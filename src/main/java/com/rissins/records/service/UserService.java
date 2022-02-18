package com.rissins.records.service;

import com.rissins.records.domain.User;
import com.rissins.records.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public int login(User user) {
        Optional<User> first = userRepository.findAll().stream()
//                .filter(m -> Objects.equals(m.getUserId(), user.getUserId()))
                .findFirst();
        User checkUser = first.filter(m -> Objects.equals(m.getUserPassword(), user.getUserPassword()))
                .orElse(null);
        if (checkUser == null) {
            return 1;
        } else {
            return 0;
        }
    }
}
