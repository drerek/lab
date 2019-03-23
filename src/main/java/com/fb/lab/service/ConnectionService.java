package com.fb.lab.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class ConnectionService {
    private static Logger log = LoggerFactory.getLogger(ConnectionService.class);

    public static Optional<Connection> getConnection(String uri, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.error("JDBC driver not found " + e.getMessage());
        }
        log.debug("Try to get connection to uri:" + uri + " user:" + user + " password:" + password);
        try {
            Connection connection = DriverManager.getConnection(uri, user, password);
            connection.setAutoCommit(false);
            log.debug("Connection to " + uri + " successfully set");
            return Optional.of(connection);
        } catch (SQLException e) {
            log.error("Connection to " + uri + " is failed." + e.getMessage());
            return Optional.empty();
        }
    }
}
