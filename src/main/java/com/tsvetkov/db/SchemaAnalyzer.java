package com.tsvetkov.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

record ColumnInfo(String name, String type) {}

public class SchemaAnalyzer {

    private static final int EXAMPLES_LIMIT = 3;

    public static String getSchemaDescription(Connection connection) throws SQLException {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        StringBuilder schemaDescription = new StringBuilder();
        List<String> tables = getTables(connection);
        for (String table : tables) {
            schemaDescription.append("Table: ").append(table).append("\n");
            schemaDescription.append(getTableDescription(connection, table));
        }

        return schemaDescription.toString();
    }

    private static String getTableDescription(Connection connection, String table) throws SQLException {
        var tableDescription = new StringBuilder();
        var columns = getColumns(connection, table);
            for (ColumnInfo col : columns) {
                tableDescription.append("  - ").append(col.name()).append(" (").append(col.type()).append(")\n");
            }
        var sampleRows = getSampleRows(connection, table, columns);

        tableDescription.append("Sample rows:\n");
        // Header
        tableDescription.append("  | ");
        for (ColumnInfo col : columns) {
            tableDescription.append(col.name()).append(" | ");
        }
        tableDescription.append("\n");
        // Rows
        for (List<String> row : sampleRows) {
            tableDescription.append("  | ");
            for (String value : row) {
                tableDescription.append(value).append(" | ");
            }
            tableDescription.append("\n");
        }
        return tableDescription.toString();
    }

    private static List<String> getTables(Connection connection) throws SQLException {
        var tables = new ArrayList<String>();
        try (ResultSet rs = connection.getMetaData().getTables(null, "public", "%", new String[]{"TABLE"})) {
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        }
        return tables;
    }

    private static List<ColumnInfo> getColumns(Connection connection, String table) throws SQLException {
        var columns = new ArrayList<ColumnInfo>();
        try (ResultSet rs = connection.getMetaData().getColumns(null, "public", table, "%")) {
            while (rs.next()) {
                columns.add(new ColumnInfo(
                        rs.getString("COLUMN_NAME"),
                        rs.getString("TYPE_NAME")
                ));
            }
        }
        return columns;
    }

    private static List<List<String>> getSampleRows(Connection connection, String table, List<ColumnInfo> columns) throws SQLException {
        var rows = new ArrayList<List<String>>();
        try (ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM \"" + table + "\" LIMIT " + EXAMPLES_LIMIT)) {
            while (rs.next()) {
                var row = new ArrayList<String>();
                for (ColumnInfo col : columns) {
                    row.add(rs.getString(col.name()));
                }
                rows.add(row);
            }
        }
        return rows;
    }
}
