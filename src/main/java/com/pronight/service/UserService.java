package com.pronight.service;

import java.util.List;
import java.util.Optional;

import com.pronight.entity.UserEntity;

public interface UserService {

    List<UserEntity> getAllUsers();

    Optional<UserEntity> getById(Long id);

    UserEntity registerUser(UserEntity user);

    Optional<UserEntity> updateUser(Long id, UserEntity user);

    boolean deleteUser(Long id);

    UserEntity login(String email, String password);

}