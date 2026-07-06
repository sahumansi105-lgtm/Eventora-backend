package com.pronight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pronight.entity.WishlistEntity;
import com.pronight.service.WishlistService;

@RestController
@RequestMapping("/wishlist")
@CrossOrigin(origins = "*")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/add/{eventId}")
    public WishlistEntity addToWishlist(
            @PathVariable Long eventId) {

        return wishlistService.addToWishlist(eventId);
    }

    @GetMapping("/my")
    public List<WishlistEntity> getMyWishlist() {

        return wishlistService.getMyWishlist();
    }

    @DeleteMapping("/remove/{eventId}")
    public String removeWishlist(
            @PathVariable Long eventId) {

        wishlistService.removeFromWishlist(eventId);

        return "Removed Successfully";
    }
}