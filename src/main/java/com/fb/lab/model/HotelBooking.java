package com.fb.lab.model;

import java.time.LocalDateTime;

public class HotelBooking {

    private Long hotelId;

    private String clientName;

    private String hotelName;

    private LocalDateTime arrivalDate;

    private LocalDateTime departureDate;

    public HotelBooking(Long hotelId,
                        String clientName,
                        String hotelName,
                        LocalDateTime arrivalDate,
                        LocalDateTime departureDate) {
        this.hotelId = hotelId;
        this.clientName = clientName;
        this.hotelName = hotelName;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }
}
