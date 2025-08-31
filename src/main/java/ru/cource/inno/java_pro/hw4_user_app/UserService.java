/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2023 VTB Group. All rights reserved.
 */

package ru.cource.inno.java_pro.hw4_user_app;

import java.util.List;
import java.util.Optional;

/**
 * UserService.
 * @author Kirill_Lustochkin
 * @since 10.08.2025
 */
public interface UserService {
    User create(String username);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    List<User> getAll();

    boolean rename(Long id, String newUsername);

    boolean delete(Long id);
}
