package com.tsvetkov;

import com.tsvetkov.ai.AIAssistant;
import com.tsvetkov.ai.AIUtils;
import com.tsvetkov.db.DatabaseManager;
import com.tsvetkov.db.SchemaAnalyzer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("Starting our text-to-sql application...");
        AIAssistant aiAssistant = AIUtils.getAIAssistant();

        try (Connection conn = DatabaseManager.getInitialConnection()) {
            String schemaDescription = SchemaAnalyzer.getSchemaDescription(conn);

            String question = "How many orders has John Smith placed?";

            String query = aiAssistant.getQuery(question, schemaDescription);

            String formattedQueryResults = getFormattedResultsFromQuery(conn, query);


            String humanAnswer = aiAssistant.explainAnswer(question, schemaDescription, query, formattedQueryResults);

            System.out.println(humanAnswer);
        }
    }

    private static String getFormattedResultsFromQuery(Connection conn, String sql) throws SQLException {
        DatabaseManager.validateQuery(sql);
        var resultsDescription = new StringBuilder();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            int columnCount = rs.getMetaData().getColumnCount();
            // Print header
            for (int i = 1; i <= columnCount; i++) {
                resultsDescription.append(rs.getMetaData().getColumnName(i));
                if (i < columnCount) resultsDescription.append(" | ");
            }
            resultsDescription.append("\n");
            // Print rows
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    resultsDescription.append(rs.getString(i));
                    if (i < columnCount) resultsDescription.append(" | ");
                }
                resultsDescription.append("\n");
            }
        }
        return resultsDescription.toString();
    }
}