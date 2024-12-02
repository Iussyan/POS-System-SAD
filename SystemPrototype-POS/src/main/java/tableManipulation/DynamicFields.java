package tableManipulation;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

public class DynamicFields {

    public enum FormatType {
        PHONE_NUMBER,
        SSN,
        ZIP_CODE,
        PRICE,
        LIMIT,
        ALPHABETICAL
    }

    public static void setTextFieldMaxLength(TextField textField, int maxLength) {
        // Create a TextFormatter with a filter function
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            if (change.isContentChange()) {
                String newText = change.getControlNewText();
                if (newText.length() <= maxLength) {
                    return change; // Allow the change
                } else {
                    // Show alert if the limit is reached
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Maximum Length Reached");
                    alert.setHeaderText(null); // No header
                    alert.setContentText("You have reached the maximum character limit.");
                    alert.showAndWait();
                    return null; // Disallow the change
                }
            }
            return change;
        });

        // Apply the TextFormatter to the TextField
        textField.setTextFormatter(formatter);
    }

    public static void setTextAreaMaxLength(TextArea textArea, int maxLength) {
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > maxLength) {
                    // Use setText to set the text directly
                    textArea.setText(newValue.substring(0, maxLength));
                }
            }
        });
    }

    public static void populateComboBox(ComboBox<String> comboBox, ResultSet rs) {
        ObservableList<String> items = FXCollections.observableArrayList();

        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                StringBuilder rowData = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    // Get the value from each column in the current row
                    String value = rs.getString(i);
                    rowData.append(value);
                    if (i < columnCount) {
                        rowData.append(" - "); // Separator between columns
                    }
                }
                items.add(rowData.toString());
            }

            comboBox.setItems(items);

        } catch (SQLException e) {
            System.err.println("Error populating ComboBox: " + e.getMessage());
        }
    }

    public static void populateSearchableComboBox(SearchableComboBox<String> searchableComboBox, ResultSet rs) {
        ObservableList<String> items = FXCollections.observableArrayList();
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                items.add(columnName);
            }
            searchableComboBox.setItems(items);
        } catch (SQLException e) {
            System.err.println("Error populating SearchableComboBox with columns: " + e.getMessage());
        }
    }

    public static void applyTextFormatter(TextField textField, FormatType formatType) {
        switch (formatType) {
            case PHONE_NUMBER:
                applyPhoneNumberFormatter(textField);
                break;
            case SSN:
                applySSNFormatter(textField);
                break;
            case ZIP_CODE:
                applyZipCodeFormatter(textField);
                break;
            case PRICE:
                applyPriceFormatter(textField);
                break;
            case LIMIT:
                applyLimitFormatter(textField);
                break;
            case ALPHABETICAL:
                applyAlphabeticalFormatter(textField);
                break;
        }
    }

    private static void applyPhoneNumberFormatter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText().replaceAll("[^0-9]", "");
            return (newText.length() <= 11) ? change : null; // Limit to 11 digits
        }));
    }

    private static void applyAlphabeticalFormatter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Allow only numbers (0-9)
            if (newText.matches("[0-9]*")) {
                return (newText.length() <= 11) ? change : null; // Limit to 11 digits
            } else {
                return null; // Reject the change if non-numerical
            }
        }));
    }

    private static void applyLimitFormatter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Allow only numbers (0-9)
            if (newText.matches("[0-9]*")) {
                return (newText.length() <= 3) ? change : null; // Limit to 3 digits
            } else {
                return null; // Reject the change if non-numerical
            }
        }));
    }

    private static void applySSNFormatter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText().replaceAll("[^0-9]", "");
            return (newText.length() <= 9) ? change : null; // Limit to 9 digits
        }));
        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() >= 9) {
                textField.setText(formatSSN(newVal));
            }
        });
    }

    private static String formatSSN(String input) {
        if (input.length() < 9) {
            return input;
        }
        return input.substring(0, 3) + "-" + input.substring(3, 5) + "-" + input.substring(5, 9);
    }

    private static void applyZipCodeFormatter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText().replaceAll("[^0-9]", "");
            return (newText.length() <= 9) ? change : null; // Limit to 9 digits (US ZIP+4)
        }));
        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() >= 5) {
                textField.setText(formatZipCode(newVal));
            }
        });
    }

    private static String formatZipCode(String input) {
        if (input.length() <= 5) {
            return input;
        }
        return input.substring(0, 5) + "-" + input.substring(5);
    }

    private static void applyPriceFormatter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Allow only numbers and one decimal point
            if (newText.matches("[0-9]*\\.?\\d*")) {
                // Limit to two decimal places
                if (newText.contains(".")) {
                    int decimalIndex = newText.indexOf('.');
                    if (decimalIndex != -1 && newText.substring(decimalIndex).length() > 3) {
                        return null; // Reject if more than two decimal places
                    }
                }
                return change;
            } else {
                return null; // Reject if non-numerical or invalid decimal
            }
        }));
        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                try {
                    double value = Double.parseDouble(newVal);
                    textField.setText(String.format("$%.2f", value));
                } catch (NumberFormatException e) {
                    textField.setText("");
                }
            }
        });
    }

    public static void applyAlphabeticalWithLimit(TextField textField, int maxLength) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Allow only letters and ensure the length does not exceed the limit
            return (newText.matches("[a-zA-Z\\s]*") && newText.length() <= maxLength) ? change : null;
        }));
    }

    public static void applyAlphabeticalSpecialWithLimit(TextField textField, int maxLength) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Allow letters, spaces, and special characters and ensure the length does not exceed the limit
            return (newText.matches("[a-zA-Z\\s\\p{Punct}]*") && newText.length() <= maxLength) ? change : null;
        }));
    }

    public static void applyAlphabeticalWithLimitArea(TextArea textArea, int maxLength) {
        textArea.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Allow only letters and ensure the length does not exceed the limit
            return (newText.matches("[a-zA-Z\\s]*") && newText.length() <= maxLength) ? change : null;
        }));
    }

    public static void applyAlphabeticalSpecialWithLimitArea(TextArea textArea, int maxLength) {
        textArea.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Allow letters, spaces, and special characters and ensure the length does not exceed the limit
            return (newText.matches("[a-zA-Z\\s\\p{Punct}]*") && newText.length() <= maxLength) ? change : null;
        }));
    }

    public static void setDatePickerFormat(DatePicker datePicker) {
        // Define the desired date format
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        // Create a StringConverter to handle the format conversion
        StringConverter<LocalDate> converter = new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        };

        // Set the converter on the DatePicker
        datePicker.setConverter(converter);
    }

    public static boolean validateDates(DatePicker manufacturedDatePicker, DatePicker expiringDatePicker) {
        LocalDate manufacturedDate = manufacturedDatePicker.getValue();
        LocalDate expiringDate = expiringDatePicker.getValue();

        if (manufacturedDate != null && expiringDate != null) {
            if (expiringDate.isBefore(manufacturedDate)) {
                // Dates are invalid
                return false;
            } else {
                // Dates are valid
                return true;
            }
        } else {
            // Handle the case where one or both dates are not selected
            System.out.println("Please select both manufactured and expiring dates.");
            return false;
        }
    }

    public static java.sql.Date toSqlDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

}
