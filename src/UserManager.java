import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;


abstract class UserManager {
    private JTextField search;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField middleInitial;
    private JComboBox<String> extensionName;
    private JComboBox<String> course;
    private JComboBox<String> yearLevel;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;

    private JTextField birthdate;
    private JTextField email;
    private JTextArea address;
    private JTextField contactNumber;

    protected UserManager(
            JTextField search,
            JTextField firstName,
            JTextField lastName,
            JTextField middleInitial,
            JComboBox<String> extensionName,
            JComboBox<String> course,
            JComboBox<String> yearLevel,
            JRadioButton maleRadioButton,
            JRadioButton femaleRadioButton,
            JTextField birthdate,
            JTextField email,
            JTextField contactNumber,
            JTextArea address ) {

        this.search = search;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInitial = middleInitial;
        this.extensionName = extensionName;
        this.course = course;
        this.yearLevel = yearLevel;
        this.maleRadioButton = maleRadioButton;
        this.femaleRadioButton = femaleRadioButton;
        this.birthdate = birthdate;
        this.email = email;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    protected abstract void register();
    protected abstract void delete();
    protected abstract void update();
    protected abstract void clearForm();
    protected abstract void search();
    protected abstract void sortTable();
    protected abstract void displayInformation(int rowToView);


    // Capitalizes the first letter of each word in a full name
    protected static String capitalizeFirstAndLastName(String lastName, String firstName) {
        int loopCount = 0;
        String fullName = lastName + " " + firstName;
        // Split the full name into an array of nameParts using whitespace characters as separators
        String[] nameParts = fullName.split("\\s");

        // StringJoiner to store the result
        StringBuilder result = new StringBuilder();

        // Loop through each namePart in the array
        for (String namePart : nameParts) {
            // Check if the namePart is not empty
            if (!namePart.isEmpty()) {
                // Capitalize the first letter using charAt and substring
                char firstLetter = Character.toUpperCase(namePart.charAt(0));

                if (loopCount == 0) {
                    // Append the capitalized first letter and the last name of the full name and add a comma with space in the end
                    result.append(firstLetter + namePart.substring(1) + ", " );
                    loopCount++;
                } else {
                    // Append the capitalized first letter and the first name of the full name
                    result.append(firstLetter + namePart.substring(1) );
                }
            }
        }

        // Convert the StringJoiner to a String
        return result.toString();
    }



    // Validate if the given date is in a valid format using regular expression
    protected static boolean isValidDateFormat(String date) {
        // Define the date format using a regular expression
        String dateFormat = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}$";

        // Compile the regular expression pattern
        Pattern pattern = Pattern.compile(dateFormat);

        // Create a matcher for the date string
        Matcher matcher = pattern.matcher(date);

        // Return true if the date matches the specified format, otherwise false
        return matcher.matches();
    }

    // Validate if the given email is in a valid format using regular expression
    protected static boolean isValidEmail(String email) {
        // Define the email format using a regular expression
        String emailFormat = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the regular expression pattern
        Pattern pattern = Pattern.compile(emailFormat);

        // Create a matcher for the email string
        Matcher matcher = pattern.matcher(email);

        // Return true if the email matches the specified format, otherwise false
        return matcher.matches();
    }

    // Validate if the given contact number is in a valid format using regular expression
    protected static boolean isValidContactNumber(String contactNumber) {
        // Define the contact number format using a regular expression
        String contactFormat = "^(09|\\+639)\\d{9}$";

        // Compile the regular expression pattern
        Pattern pattern = Pattern.compile(contactFormat);

        // Create a matcher for the contact number string
        Matcher matcher = pattern.matcher(contactNumber);

        // Return true if the contact number matches the specified format, otherwise false
        return matcher.matches();
    }

    protected void showInformationMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    protected void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    // Getters
    protected JTextField getSearch() {
        return search;
    }

    protected JTextField getFirstName() {
        return firstName;
    }

    protected JTextField getLastName() {
        return lastName;
    }

    protected JTextField getMiddleInitial() {
        return middleInitial;
    }

    protected JComboBox<String> getExtensionName() {
        return extensionName;
    }

    protected JComboBox<String> getCourse() {
        return course;
    }

    protected JComboBox<String> getYearLevel() {
        return yearLevel;
    }

    protected JRadioButton getMaleRadioButton() {
        return maleRadioButton;
    }

    protected JRadioButton getFemaleRadioButton() {
        return femaleRadioButton;
    }

    protected JTextField getBirthdate() {
        return birthdate;
    }

    protected JTextField getEmail() {
        return email;
    }

    protected JTextArea getAddress() {
        return address;
    }

    protected JTextField getContactNumber() {
        return contactNumber;
    }


}