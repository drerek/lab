package com.fb.lab;

import com.fb.lab.model.FlyBooking;
import com.fb.lab.model.HotelBooking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static com.fb.lab.service.ConnectionService.getConnection;
import static com.fb.lab.service.TransactionService.mainTransaction;

@SpringBootApplication
public class LabApplication implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(LabApplication.class);
    private final Environment env;

    @Autowired
    public LabApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(LabApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.debug("Try to get connections");
        Optional<Connection> flightConnection = getConnection("jdbc:postgresql://localhost:5432/postgres",
                env.getProperty("postgres.user.name"),
                env.getProperty("postgres.user.password"));
        Optional<Connection> hotelConnection = getConnection("jdbc:postgresql://localhost:5432/postgres",
                env.getProperty("postgres.user.name"),
                env.getProperty("postgres.user.password"));

        FlyBooking flight = new FlyBooking(
                1L,
                "Bob",
                222,
                "Madrid",
                "Kiev",
                LocalDate.of(2014, 1, 1)
        );

        HotelBooking hotel = new HotelBooking(
                1L,
                "Bob",
                "Arr",
                LocalDate.of(2014, 1, 1),
                LocalDate.of(2014, 1, 3)
        );
        if (flightConnection.isPresent() && hotelConnection.isPresent()) {
            try {
                mainTransaction(flightConnection.get(),
                        hotelConnection.get(),
                        flight,
                        hotel);
            } catch (SQLException e) {
                log.error("Error in main transaction" + e.getMessage());
            } finally {

                log.debug("Closing connections");
                try {
                    flightConnection.get().close();
                    hotelConnection.get().close();
                } catch (SQLException e) {
                    log.error("Cant close connections" + e.getMessage());
                }
            }
        }
    }
}
