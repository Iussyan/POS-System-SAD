package tableManipulation;

import java.sql.*;
import java.util.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DynamicTableView {

    // Example: Call sp_get_product_details
//        try (CallableStatement cs = conn.prepareCall("{call sp_get_product_details(?, ?)}")) {
//            cs.setString(1, "name"); // Set search_type
//            cs.setString(2, "Product A"); // Set search_value
//            cs.execute();
//            ResultSet rs = cs.getResultSet();
//            DynamicTableView.populateTable(tableView, rs); // Pass your TableView
//        } 
    /**
     * Populates a TableView with data from a ResultSet. Automatically generates
     * columns and fills rows with data.
     *
     * @param tableView The TableView to populate.
     * @param resultSet The ResultSet containing data.
     * @param tableCopy
     * @throws SQLException If an SQL error occurs during data retrieval.
     */
    public static void populateTable(TableView<ObservableList<String>> tableView, ResultSet resultSet, TableView<ObservableList<String>> tableCopy) throws SQLException {
        // 1. Get Column Names
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Create a list to hold column names
        List<String> columnNames = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnLabel(i));
        }

        // 2. Create Table Columns
        tableView.getColumns().clear(); // Clear any existing columns
        for (int i = 0; i < columnNames.size(); i++) {
            final int columnIndex = i; // Make index effectively final for lambda
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames.get(i));
            column.setCellValueFactory(cellData -> {
                ObservableList<String> rowData = cellData.getValue();
                return (rowData != null && columnIndex < rowData.size())
                        ? new ReadOnlyStringWrapper(rowData.get(columnIndex))
                        : new ReadOnlyStringWrapper(""); // Handle empty values
            });
            tableView.getColumns().add(column);
        }

        // 3. Populate Table Data
        ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();
        while (resultSet.next()) {
            ObservableList<String> rowData = FXCollections.observableArrayList();
            for (int i = 1; i <= columnCount; i++) {
                rowData.add(Optional.ofNullable(resultSet.getString(i)).orElse("")); // Handle nulls
            }
            tableData.add(rowData);
        }
        tableView.setItems(tableData);

        updateTable(tableView, tableCopy);
    }

    /**
     * Sorts the TableView based on a specific column.
     *
     * @param tableView The TableView to sort.
     * @param columnIndex The index of the column to sort by.
     * @param ascending True for ascending, false for descending order.
     * @param numeric True if the column contains numeric data, false for
     * alphabetic.
     */
    public static void sortTable(TableView<ObservableList<String>> tableView, int columnIndex, boolean ascending, boolean numeric) {
        ObservableList<ObservableList<String>> data = tableView.getItems();

        if (columnIndex < 0 || columnIndex >= tableView.getColumns().size()) {
            throw new IllegalArgumentException("Invalid column index: " + columnIndex);
        }

        FXCollections.sort(data, (row1, row2) -> {
            String value1 = row1.get(columnIndex);
            String value2 = row2.get(columnIndex);

            if (numeric) {
                // Handle numeric sorting
                double num1 = parseDoubleSafe(value1);
                double num2 = parseDoubleSafe(value2);
                return ascending ? Double.compare(num1, num2) : Double.compare(num2, num1);
            } else {
                // Handle alphabetical sorting
                return ascending ? value1.compareToIgnoreCase(value2) : value2.compareToIgnoreCase(value1);
            }
        });
    }

    public static void updateTable(TableView<ObservableList<String>> tableView, TableView<ObservableList<String>> tableCopy) {
        tableCopy.getColumns().clear();
        tableCopy.getItems().clear();

        tableView.getColumns().stream().map(column -> {
            TableColumn<ObservableList<String>, String> copiedColumn = new TableColumn<>(column.getText());
            if (column.getCellValueFactory() instanceof PropertyValueFactory) {
                PropertyValueFactory factory = (PropertyValueFactory) column.getCellValueFactory();
                copiedColumn.setCellValueFactory(new PropertyValueFactory<>(factory.getProperty()));
            }
            return copiedColumn;
        }).map(copiedColumn -> {
            tableCopy.getColumns().add(copiedColumn);
            return copiedColumn;
        }).forEachOrdered(_item -> {
            tableCopy.getItems().addAll(tableView.getItems());
        });
    }

    /**
     * Safely parses a string to a double, returning Double.MIN_VALUE for
     * invalid values.
     *
     * @param value The string to parse.
     * @return The parsed double or Double.MIN_VALUE if parsing fails.
     */
    private static double parseDoubleSafe(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return Double.MIN_VALUE; // Default value for invalid numbers
        }
    }

}
