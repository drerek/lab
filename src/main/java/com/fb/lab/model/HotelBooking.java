package com.fb.lab.model;

import java.time.LocalDate;

public class HotelBooking {

    private Long hotelId;

    private String clientName;

    private String hotelName;

    private LocalDate arrivalDate;

    private LocalDate departureDate;

    public HotelBooking(Long hotelId,
                        String clientName,
                        String hotelName,
                        LocalDate arrivalDate,
                        LocalDate departureDate) {
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
        return "'" + clientName + "'";
    }

    public String getHotelName() {
        return "'" + hotelName + "'";
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }
}
