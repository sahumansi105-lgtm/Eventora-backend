package com.pronight.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pronight.entity.EventEntity;
import com.pronight.entity.UserEntity;
import com.pronight.entity.WishlistEntity;
import com.pronight.repository.EventRepo;
import com.pronight.repository.UserRepo;
import com.pronight.repository.WishlistRepo;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepo wishlistRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventRepo eventRepo;

    private UserEntity getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    @Override
    public WishlistEntity addToWishlist(Long eventId) {

        UserEntity user = getLoggedInUser();

        EventEntity event = eventRepo.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        // Check if already exists
        if (wishlistRepo.findByUserAndEvent(user, event).isPresent()) {
            throw new RuntimeException("Event already exists in wishlist");
        }

        WishlistEntity wishlist = new WishlistEntity();
        wishlist.setUser(user);
        wishlist.setEvent(event);

        return wishlistRepo.save(wishlist);
    }

    @Override
    public List<WishlistEntity> getMyWishlist() {

        UserEntity user = getLoggedInUser();

        return wishlistRepo.findByUser(user);
    }

    @Override
    public void removeFromWishlist(Long eventId) {

        UserEntity user = getLoggedInUser();

        EventEntity event = eventRepo.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        WishlistEntity wishlist = wishlistRepo
                .findByUserAndEvent(user, event)
                .orElseThrow(() ->
                        new RuntimeException("Wishlist item not found"));

        wishlistRepo.delete(wishlist);
    }
}