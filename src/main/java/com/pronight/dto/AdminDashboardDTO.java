package com.pronight.dto;

import java.util.List;

public class AdminDashboardDTO {

    private Long totalEvents;
    private Long totalPasses;
    private Long totalBookings;
    private Long totalUsers;
    private Double totalRevenue;

    private List<RecentBookingDTO> recentBookings;

    private List<LowStockPassDTO> lowStockPasses;

    public Long getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(Long totalEvents) {
        this.totalEvents = totalEvents;
    }

    public Long getTotalPasses() {
        return totalPasses;
    }

    public void setTotalPasses(Long totalPasses) {
        this.totalPasses = totalPasses;
    }

    public Long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(Long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public List<?> getRecentBookings() {
        return recentBookings;
    }

    public void setRecentBookings(List<RecentBookingDTO> recentBookings) {
        this.recentBookings = recentBookings;
    }

    public List<?> getLowStockPasses() {
        return lowStockPasses;
    }

    public void setLowStockPasses(List<LowStockPassDTO> lowStockPasses) {
        this.lowStockPasses = lowStockPasses;
    }
}