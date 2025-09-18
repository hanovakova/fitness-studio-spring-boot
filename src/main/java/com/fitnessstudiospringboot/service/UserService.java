package com.fitnessstudiospringboot.service;

import com.fitnessstudiospringboot.model.User;
import com.fitnessstudiospringboot.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository repo;

    public UserService(UserRepository userRepository) {
        this.repo = userRepository;
    }

    @Transactional
    public void add(User user) {
        repo.save(user);
    }

    public User validateUser(final String username, final String password) {
        return repo.findByUsernameAndPassword(username, password)
                .orElse(null);
    }
}
