/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2023 VTB Group. All rights reserved.
 */

package ru.cource.inno.java_pro.hw4_user_app;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * UserServiceImpl.
 * @author Kirill_Lustochkin
 * @since 10.08.2025
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserDao dao;


    @Transactional
    public User create(String username) {
        return dao.save(new User().setUsername(username));
    }

    public Optional<User> findById(Long id) {
        return dao.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return dao.findByUsername(username);
    }

    public List<User> getAll() {
        return dao.findAll();
    }

    @Transactional
    public boolean rename(Long id, String newUsername) {
        return dao.findById(id)
            .filter(u -> !Objects.equals(u.getUsername(), newUsername))
            .filter(u -> dao.findByUsername(newUsername).isEmpty())
            .map(u -> { u.setUsername(newUsername); return u; })
            .map(dao::save)
            .isPresent();
    }

    @Transactional
    public boolean delete(Long id) {
        return dao.findById(id)
            .map(user -> {
                dao.delete(user);
                return true;
            })
            .orElse(false);
    }

}
