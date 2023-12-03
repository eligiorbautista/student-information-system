import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

abstract class UserManager {
    private JTextField search;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField middleInitial;
    private JComboBox<String> extensionName;
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
            JRadioButton maleRadioButton,
            JRadioButton femaleRadioButton,
            JTextField birthdate,
            JTextField email,
            JTextField contactNumber,
            JTextArea address) {
        this.search = search;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInitial = middleInitial;
        this.extensionName = extensionName;
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

    protected static String capitalizeFirstAndLastName(String lastName, String firstName) {
        String fullName = lastName + " " + firstName;
        String[] nameParts = fullName.split("\\s");
        StringBuilder result = new StringBuilder();

        for (String namePart : nameParts) {
            if (!namePart.isEmpty()) {
                char firstLetter = Character.toUpperCase(namePart.charAt(0));
                if (result.length() == 0) {
                    result.append(firstLetter).append(namePart.substring(1)).append(", ");
                } else {
                    result.append(firstLetter).append(namePart.substring(1));
                }
            }
        }
        return result.toString();
    }

    protected static boolean isValidDateFormat(String date) {
        String dateFormat = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}$";
        Pattern pattern = Pattern.compile(dateFormat);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    protected static boolean isValidEmail(String email) {
        String emailFormat = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailFormat);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    protected static boolean isValidContactNumber(String contactNumber) {
        String contactFormat = "^(09|\\+639)\\d{9}$";
        Pattern pattern = Pattern.compile(contactFormat);
        Matcher matcher = pattern.matcher(contactNumber);
        return matcher.matches();
    }

    protected void showInformationMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    protected void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

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
