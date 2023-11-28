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