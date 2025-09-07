/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2023 VTB Group. All rights reserved.
 */

package ru.cource.inno.java_pro.hw4_user_app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * UserDao.
 * @author Kirill_Lustochkin
 * @since 10.08.2025
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}

