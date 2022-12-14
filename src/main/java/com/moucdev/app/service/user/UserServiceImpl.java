package com.moucdev.app.service.user;

import com.moucdev.app.entities.User;
import com.moucdev.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll();
    }
}
