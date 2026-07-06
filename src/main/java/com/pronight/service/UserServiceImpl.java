package com.pronight.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pronight.entity.UserEntity;
import com.pronight.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> getAllUsers() {
        return repo.findByActiveTrue();
    }

    @Override
    public Optional<UserEntity> getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public UserEntity registerUser(UserEntity user) {

        if (repo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (repo.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new RuntimeException("Phone Number already exists");
        }

        user.setRole("USER");
        user.setMembership_level("BRONZE");
        user.setLoyaltyPoints(0);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return repo.save(user);
    }

    @Override
    public Optional<UserEntity> updateUser(Long id, UserEntity user) {

        Optional<UserEntity> existingUser = repo.findById(id);

        if (existingUser.isPresent()) {

            UserEntity u = existingUser.get();

            u.setFullName(user.getFullName());
            u.setEmail(user.getEmail());
            u.setPhoneNumber(user.getPhoneNumber());

            return Optional.of(repo.save(u));
        }

        return Optional.empty();
    }

    @Override
    public boolean deleteUser(Long id) {

        Optional<UserEntity> user = repo.findById(id);

        if (user.isPresent()) {
            try {
                repo.deleteById(id);
                repo.flush();
            } catch (DataIntegrityViolationException ex) {
                UserEntity existing = user.get();
                existing.setActive(false);
                repo.save(existing);
            }
            return true;
        }

        return false;
    }

    @Override
    public UserEntity login(String email, String password) {
        UserEntity user = repo.findByEmail(email).orElse(null);

        if (user == null) {
            return null;
        }

        if (Boolean.FALSE.equals(user.getActive())) {
            return null;
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        // Temporary migration fallback for old users that were saved with plain-text passwords.
        if (password.equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(password));
            return repo.save(user);
        }

        return null;
    }
}
