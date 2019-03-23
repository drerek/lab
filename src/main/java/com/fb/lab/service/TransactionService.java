package com.fb.lab.service;

import com.fb.lab.model.FlyBooking;
import com.fb.lab.model.HotelBooking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionService {
    private static Logger log = LoggerFactory.getLogger(TransactionService.class);

    public static void mainTransaction(Connection flightConnection,
                                       Connection hotelConnection,
                                       FlyBooking flight,
                                       HotelBooking hotel) throws SQLException {
        int flight_status = 0;
        int hotel_status = 0;

        String flightInsert =
                "INSERT INTO " +
                        "fly_booking(booking_id, client_name, flight_number, from_place, to_place, flight_date)" +
                        " VALUES(" +
                        flight.getBookingId() + "," +
                        flight.getClientName() + "," +
                        flight.getFlightNumber() + "," +
                        flight.getFromPlace() + "," +
                        flight.getToPlace() + "," +
                        "'" + java.sql.Date.valueOf(flight.getFlightDate()) + "'" + ")";

        String hotelInsert =
                "INSERT INTO " +
                        "hotel_booking(hotel_id, client_name, hotel_name, arrival_date, departure_date)" +
                        " VALUES(" +
                        hotel.getHotelId() + "," +
                        hotel.getClientName() + "," +
                        hotel.getHotelName() + "," +
                        "'" + java.sql.Date.valueOf(hotel.getArrivalDate()) + "'" + "," +
                        "'" + java.sql.Date.valueOf(hotel.getDepartureDate()) + "'" + ")";

        try {
            Statement stmt = flightConnection.createStatement();
            stmt.executeUpdate(flightInsert);
            log.debug("Executing of update fly_booking");
        } catch (SQLException e) {
            flight_status = -1;
            log.error("Cant update fly_booking " + e.getMessage());
        }

        try {
            Statement insertHotel = hotelConnection.createStatement();
            insertHotel.executeUpdate(hotelInsert);
            log.debug("Executing of update hotel_booking");
        } catch (SQLException e) {
            hotel_status = -1;
            log.error("Cant update hotel_booking" + e.getMessage());
        }

        if ((flight_status != -1) && (hotel_status != -1)) {
            log.debug("If clause!" + "flight_status=" + flight_status + " hotel_status=" + hotel_status);
            try {
                log.debug("Try to commit flight");
                flightConnection.commit();
                log.debug("Try to commit hotel");
                hotelConnection.commit();
            } catch (SQLException e) {
                log.error("Exception when commit " + e.getMessage());
                log.error("Rollback flight");
                flightConnection.rollback();
                log.error("Rollback hotel");
                hotelConnection.rollback();
            }
        } else {
            log.debug("Else clause!" + "flight_status=" + flight_status + " hotel_status=" + hotel_status);
            log.debug("Rollback flight");
            flightConnection.rollback();
            log.debug("Rollback hotel");
            hotelConnection.rollback();
        }
    }
}
