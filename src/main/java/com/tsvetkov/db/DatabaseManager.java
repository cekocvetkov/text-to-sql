package com.tsvetkov.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/tts-db";
    private static final String USER = "tts-user";
    private static final String PASSWORD = "tts-pass";

    public static Connection getInitialConnection() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to PostgreSQL!");

            String initSql = loadSQL();
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(initSql);
                System.out.println("Schema initialized.");
            }
        return connection;

    }

    public static String loadSQL() throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/main/resources/init.sql")));
    }

    public static void validateQuery(String sql) {
        // Basic validation - only allow SELECT statements
        String upperSql = sql.toUpperCase().trim();

        if(!upperSql.startsWith("SELECT") ||
                upperSql.contains("DROP") ||
                upperSql.contains("DELETE") ||
                upperSql.contains("INSERT") ||
                upperSql.contains("UPDATE") ||
                upperSql.contains("ALTER") ||
                upperSql.contains("CREATE")) {
            throw new RuntimeException("Invalid query:\n"+sql);
        }
    }
}