package com.pronight.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pronight.entity.EventEntity;
import com.pronight.entity.PassEntity;
import com.pronight.entity.BookingEntity.BookingStatus;
import com.pronight.repository.BookingRepo;
import com.pronight.repository.EventRepo;
import com.pronight.repository.PassRepo;

@Service
public class PassService {

    @Autowired
    private PassRepo passRepository;

    @Autowired
    private EventRepo eventRepository;

    @Autowired
    private BookingRepo bookingRepository;

    public PassEntity createPass(Long eventId, PassEntity pass) {

        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        pass.setEvent(event);

        // REQUIRED VALIDATION
        if (pass.getPassType() == null || pass.getPassName() == null ||
            pass.getPrice() == null || pass.getTotalQuantity() == null) {
            throw new RuntimeException("Missing required fields");
        }

        // DEFAULT VALUES (IMPORTANT FIX FOR NULL ISSUE)
        pass.setSoldQuantity(pass.getSoldQuantity() == null ? 0 : pass.getSoldQuantity());
        pass.setAvailableQuantity(
            pass.getAvailableQuantity() == null ? pass.getTotalQuantity() : pass.getAvailableQuantity()
        );

        pass.setCurrency(pass.getCurrency() == null ? "INR" : pass.getCurrency());
        pass.setStatus(pass.getStatus() == null ? "AVAILABLE" : pass.getStatus());
        pass.setMinPerBooking(pass.getMinPerBooking() == null ? 1 : pass.getMinPerBooking());
        pass.setMaxPerBooking(pass.getMaxPerBooking() == null ? 10 : pass.getMaxPerBooking());
        pass.setMaxPerUser(pass.getMaxPerUser() == null ? 10 : pass.getMaxPerUser());

        return refreshAvailability(passRepository.save(pass));
    }
    public List<PassEntity> getAllPasses() {
        return passRepository.findAll()
                .stream()
                .map(this::refreshAvailability)
                .toList();
    }

    public PassEntity getPassById(Long passId) {
        return refreshAvailability(passRepository.findById(passId)
                .orElseThrow(() -> new RuntimeException("Pass not found")));
    }

    public List<PassEntity> getPassesByEvent(Long eventId) {
        return passRepository.findByEventEventId(eventId)
                .stream()
                .map(this::refreshAvailability)
                .toList();
    }

    public List<PassEntity> getPassesByType(String passType) {
        return passRepository.findByPassTypeIgnoreCase(passType);
    }

    public List<PassEntity> getPassesByStatus(String status) {
        return passRepository.findByStatus(status.toUpperCase());
    }

    public List<PassEntity> getLowStockPasses(Integer quantity) {
        return getAllPasses()
                .stream()
                .filter(pass -> pass.getAvailableQuantity() <= quantity)
                .toList();
    }

    public PassEntity updatePass(Long passId, PassEntity passData) {
        PassEntity pass = getPassById(passId);

        pass.setPassType(passData.getPassType());
        pass.setPassName(passData.getPassName());
        pass.setDescription(passData.getDescription());
        pass.setPrice(passData.getPrice());
        pass.setDiscountPrice(passData.getDiscountPrice());
        pass.setCurrency(passData.getCurrency());
        pass.setTotalQuantity(passData.getTotalQuantity());
        Integer sold = pass.getSoldQuantity() == null ? 0 : pass.getSoldQuantity();

        pass.setAvailableQuantity(
            passData.getTotalQuantity() - sold
        );
        pass.setMinPerBooking(passData.getMinPerBooking());
        pass.setMaxPerBooking(passData.getMaxPerBooking());
        pass.setMaxPerUser(passData.getMaxPerUser());
        pass.setEntryGate(passData.getEntryGate());
        pass.setSeatingType(passData.getSeatingType());
        pass.setHighlightLabel(passData.getHighlightLabel());
        pass.setSaleStartDateTime(passData.getSaleStartDateTime());
        pass.setSaleEndDateTime(passData.getSaleEndDateTime());
        pass.setStatus(passData.getStatus());
        pass.setBasePrice(passData.getBasePrice());
        pass.setFirstTierLimit(passData.getFirstTierLimit());
        pass.setFirstTierPrice(passData.getFirstTierPrice());
        pass.setSecondTierLimit(passData.getSecondTierLimit());
        pass.setSecondTierPrice(passData.getSecondTierPrice());
        pass.setFinalTierPrice(passData.getFinalTierPrice());
        pass.setFoodBenefits(passData.getFoodBenefits());

        return refreshAvailability(passRepository.save(pass));
    }

    public String deletePass(Long passId) {
        PassEntity pass = getPassById(passId);
        passRepository.delete(pass);   // ✅ actual DB delete
        return "Pass deleted successfully";
    }
    
    public Double calculateCurrentPrice(PassEntity pass) {

        Integer sold = pass.getSoldQuantity() == null
                ? 0
                : pass.getSoldQuantity();

        if (
            pass.getFirstTierLimit() != null &&
            pass.getFirstTierPrice() != null &&
            sold <= pass.getFirstTierLimit()
        ) {
            return pass.getFirstTierPrice();
        }

        if (
            pass.getSecondTierLimit() != null &&
            pass.getSecondTierPrice() != null &&
            sold <= pass.getSecondTierLimit()
        ) {
            return pass.getSecondTierPrice();
        }

        return pass.getFinalTierPrice() != null
                ? pass.getFinalTierPrice()
                : pass.getPrice();
    }

    public PassEntity refreshAvailability(PassEntity pass) {
        if (pass == null || pass.getPassId() == null || pass.getTotalQuantity() == null) {
            return pass;
        }

        Long activeReserved = bookingRepository.getTotalTicketsForPass(
                pass.getPassId(),
                Arrays.asList(
                        BookingStatus.PENDING,
                        BookingStatus.CONFIRMED,
                        BookingStatus.CANCELLATION_REQUESTED
                )
        );

        Long confirmedSold = bookingRepository.getTotalTicketsForPass(
                pass.getPassId(),
                List.of(BookingStatus.CONFIRMED)
        );

        int reserved = activeReserved == null ? 0 : activeReserved.intValue();
        int sold = confirmedSold == null ? 0 : confirmedSold.intValue();
        int remaining = Math.max(0, pass.getTotalQuantity() - reserved);

        pass.setSoldQuantity(sold);
        pass.setAvailableQuantity(remaining);

        if (remaining <= 0) {
            pass.setStatus("SOLD_OUT");
        } else if (pass.getStatus() == null || pass.getStatus().isBlank() || "SOLD_OUT".equals(pass.getStatus())) {
            pass.setStatus("AVAILABLE");
        }

        return passRepository.save(pass);
    }
}
