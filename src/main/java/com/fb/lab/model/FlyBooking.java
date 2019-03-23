package com.fb.lab.model;

import java.time.LocalDate;

public class FlyBooking {

    private Long bookingId;

    private String clientName;

    private Integer flightNumber;

    private String fromPlace;

    private String toPlace;

    private LocalDate flightDate;

    public FlyBooking(Long bookingId,
                      String clientName,
                      Integer flightNumber,
                      String fromPlace,
                      String toPlace,
                      LocalDate flightDate) {
        this.bookingId = bookingId;
        this.clientName = clientName;
        this.flightNumber = flightNumber;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.flightDate = flightDate;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public String getClientName() {
        return "'" + clientName + "'";
    }

    public Integer getFlightNumber() {
        return flightNumber;
    }

    public String getFromPlace() {
        return "'" + fromPlace + "'";
    }

    public String getToPlace() {
        return "'" + toPlace + "'";
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }
}
