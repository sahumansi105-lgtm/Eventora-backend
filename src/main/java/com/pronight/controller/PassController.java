package com.pronight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pronight.entity.PassEntity;
import com.pronight.service.PassService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/passes")
public class PassController {

    @Autowired
    private PassService passService;

    @PostMapping("/event/{eventId}")
    public PassEntity createPass(
            @PathVariable Long eventId,
            @RequestBody PassEntity pass
    ) {
        return passService.createPass(eventId, pass);
    }

    @GetMapping
    public List<PassEntity> getAllPasses() {
        return passService.getAllPasses();
    }

    @GetMapping("/{passId}")
    public PassEntity getPassById(@PathVariable Long passId) {
        return passService.getPassById(passId);
    }

    @GetMapping("/event/{eventId}")
    public List<PassEntity> getPassesByEvent(@PathVariable Long eventId) {
        return passService.getPassesByEvent(eventId);
    }

    @GetMapping("/type/{passType}")
    public List<PassEntity> getPassesByType(@PathVariable String passType) {
        return passService.getPassesByType(passType);
    }

    @GetMapping("/status/{status}")
    public List<PassEntity> getPassesByStatus(@PathVariable String status) {
        return passService.getPassesByStatus(status);
    }

    @GetMapping("/low-stock/{quantity}")
    public List<PassEntity> getLowStockPasses(@PathVariable Integer quantity) {
        return passService.getLowStockPasses(quantity);
    }

    @PutMapping("/{passId}")
    public PassEntity updatePass(
            @PathVariable Long passId,
            @RequestBody PassEntity pass
    ) {
        return passService.updatePass(passId, pass);
    }

    @DeleteMapping("/{passId}")
    public String deletePass(@PathVariable Long passId) {
        return passService.deletePass(passId);
    }
}