package com.rissins.records.service;

import com.rissins.records.domain.constant.Status;
import com.rissins.records.domain.User;
import com.rissins.records.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAllByUser() {
        return userRepository.findAll();
    }

//    public int login(User user) {
//        Optional<User> first = userRepository.findAll().stream()
//                .filter(m -> Objects.equals(m.getUserId(), user.getUserId()))
//                .findFirst();
//        User checkUser = first.filter(m -> Objects.equals(m.getUserPassword(), user.getUserPassword()))
//                .orElse(null);
//        if (checkUser == null) {
//            return 1;
//        } else {
//            return 0;
//        }
//    }
    public Status login(User user) {
        return findByUserId(user.getUserId()).getUserPassword().equals(user.getUserPassword()) ? Status.ACCEPTED : Status.DENIED;
    }

    @Transactional(readOnly = true)
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}
