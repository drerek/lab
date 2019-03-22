package com.fb.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fb.lab.model.*;

import javax.swing.text.html.Option;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static java.lang.System.exit;

@SpringBootApplication
public class LabApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(LabApplication.class, args);
		Optional<Connection> flightConnection = getConnection("localhost:5432/fly_booking",
				"postgres",
				"root");
		Optional<Connection> hotelConnection = getConnection("localhost:5432/hotel_booking",
				"postgres",
				"root");

		FlyBooking flight = new FlyBooking(
				1L,
				"Bob",
				222,
				"Madrid",
				"Kiev",
				LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30)
		);

		HotelBooking hotel = new HotelBooking(
				1L,
				"Bob",
				"Arr",
				LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30),
				LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30)
		);

		if(flightConnection.isPresent() && hotelConnection.isPresent()){
            mainTransaction(flightConnection.get(),
                        hotelConnection.get(),
                        flight,
                        hotel);
        }
	}

	private static void mainTransaction(Connection flightConnection,
                   Connection hotelConnection,
                   FlyBooking flight,
                   HotelBooking hotel ) throws SQLException {
	    Integer flight_status = 0;
	    Integer hotel_status = 0;

	    String flightInsert = "INSERT INTO fly_booking(booking_id, client_name, flight_number, from_place, to_place, flight_date)\n" +
            "VALUES(" +
                flight.getFlightDate() +
                "," + flight.getClientName() +
                "," + flight.getFlightNumber() +
                "," + flight.getFromPlace() +
                "," + flight.getToPlace() +
                "," + flight.getFlightDate() + ")";

	    String hotelInsert = "INSERT INTO hotel_booking(hotel_id, client_name, hotel_name, arrival_date, departure_date)\n" +
        "VALUES(" +
                hotel.getHotelId() +
                "," + hotel.getHotelId() +
                "," + hotel.getClientName() +
                "," + hotel.getHotelName() +
                "," + hotel.getArrivalDate() +
                "," + hotel.getDepartureDate() + ")";
        try{
            Statement stmt = flightConnection.createStatement();
            stmt.executeUpdate(flightInsert);
            System.out.println("Executing of update fly_booking");
        }catch (SQLException e ){
            flight_status = -1;
            e.printStackTrace();
        }

        try{
            Statement insertHotel = hotelConnection.createStatement();
            insertHotel.executeUpdate(hotelInsert);
        }catch (SQLException e ){
            hotel_status = -1;
            e.printStackTrace();
        }

        if((flight_status == -1)||(hotel_status == -1)){
            try {
                flightConnection.commit();
                hotelConnection.commit();
            } catch (SQLException e) {
                    flightConnection.rollback();
                    hotelConnection.rollback();
            }
        }else{
                flightConnection.rollback();
                hotelConnection.rollback();
        }

        flightConnection.close();
        hotelConnection.close();
    }

	private static Optional<Connection> getConnection(String uri, String user, String password){
		try (Connection connection = DriverManager.getConnection(uri,user,password)){
		    connection.setAutoCommit(false);
		    System.out.println("Connection to " + uri + " successfully set");
			return Optional.of(connection);
		}catch (SQLException e) {
			System.out.println("Connection to " + uri + " is failed.");
			e.printStackTrace();
			return Optional.empty();
		}
    }

}
