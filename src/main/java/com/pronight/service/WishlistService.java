package com.pronight.service;

import java.util.List;

import com.pronight.entity.WishlistEntity;

public interface WishlistService {

    WishlistEntity addToWishlist(Long eventId);

    List<WishlistEntity> getMyWishlist();

    void removeFromWishlist(Long eventId);
}