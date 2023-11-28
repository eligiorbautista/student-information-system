import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManager extends UserManager {

    private DefaultTableModel model;
    private JTable studentsTable;
    private JComboBox<String> sortComboBox;


    private String adminPassword = "1234";
    public StudentManager( JTextField search, JTextField firstName, JTextField lastName, JTextField middleInitial, JComboBox<String> extensionName, JComboBox<String> course, JComboBox<String> yearLevel, JRadioButton maleRadioButton, JRadioButton femaleRadioButton, JTextField birthdate, JTextField email, JTextField contactNumber, JTextArea address, JTable studentsTable, DefaultTableModel model, JComboBox<String> sortComboBox) {
        super(search, firstName, lastName, middleInitial, extensionName, course, yearLevel, maleRadioButton, femaleRadioButton, birthdate, email, contactNumber, address);
        this.studentsTable = studentsTable;
        this.sortComboBox = sortComboBox;
        this.model = model;
    }


    public void register() {
        // Get user input
        String firstName = getFirstName().getText();
        String lastName = getLastName().getText();
        String middleInitial = getMiddleInitial().getText();
        String extensionName = (getExtensionName().getSelectedIndex() > 0)
                ? getExtensionName().getSelectedItem().toString()
                : "";
        String gender = (getMaleRadioButton().isSelected()) ? "M" : (getFemaleRadioButton().isSelected()) ? "F" : "";
        String course = getCourse().getSelectedItem().toString();
        String year = getYearLevel().getSelectedItem().toString();
        String birthdate = getBirthdate().getText();
        String address = getAddress().getText();
        String email = getEmail().getText();
        String contactNumber = getContactNumber().getText();

        // Validation flags
        boolean emailValid = isValidEmail(email);
        boolean contactNumberValid = isValidContactNumber(contactNumber);
        boolean inputFieldsNotEmpty = true;


        // Generate student ID
        String studentId = generateStudentId();

        // Validate non-blank textfields
        String[] inputFields = {firstName, lastName, gender, birthdate, address, email, contactNumber};
        for (String field : inputFields) {
            if (field.isEmpty()) {
                inputFieldsNotEmpty = false;
                break;
            }
        }

        // Validate date format
        boolean birthdateValid = isValidDateFormat(birthdate);

        // Construct full name
        String fullName = String.format("%s, %s %s %s", lastName, firstName, extensionName, middleInitial);

        // Process if all fields are not empty and birthdate is valid
        if (inputFieldsNotEmpty) {
            // Check if email and contact number are valid
            if (emailValid && contactNumberValid) {
                // Check if comboboxes have valid selections
                if (getCourse().getSelectedIndex() == 0) {
                    showErrorMessage("Please select a course.", "Invalid Course Selection");
                } else if (gender.isEmpty()) {
                    showErrorMessage("Please select a gender.", "Gender Not Selected");
                } else if (getYearLevel().getSelectedIndex() == 0) {
                    showErrorMessage("Please select a year level.", "Invalid Year Level Selection");
                } else if (!birthdateValid) {
                    // Show error if any field is empty or birthdate is not valid
                    showErrorMessage("Please enter a valid date in the format MM/DD/YYYY.", "Invalid Date Format");
                } else {
                    if (isEmailRegistered(email)){
                        showErrorMessage("Email is already registered.", "Duplicate Email");

                    } else {
                        if (isContactNumberRegistered(contactNumber)){
                            showErrorMessage("Contact number is already registered.", "Duplicate Contact Number");

                        } else {
                            // Add a new row to the table model
                            model.addRow(new Object[]{studentId, fullName, course, year, gender, birthdate, email, contactNumber, address});
                            showInformationMessage("User added successfully.", "Registration Success");
                            clearForm();
                        }
                    }
                }
            } else {
                // Show error if email or contact number is not valid
                showErrorMessage("Please enter a valid email and contact number.", "Invalid Email or Contact Number");
            }
        } else {
            // Show error if any field is empty
            showErrorMessage("Please fill all the textfields.", "Unable to process request.");
        }
    }

    // Function to check if an email is already registered
    private boolean isEmailRegistered(String newEmail) {
        for (int row = 0; row < model.getRowCount(); row++) {
            String existingEmail = (String) model.getValueAt(row, 6);
            if (newEmail.equals(existingEmail)) {
                return true; // Email is already registered
            }
        }
        return false; // Email is not registered
    }

    // Function to check if a contact number is already registered
    private boolean isContactNumberRegistered(String newContactNumber) {
        for (int row = 0; row < model.getRowCount(); row++) {
            String existingContactNumber = (String) model.getValueAt(row, 7);
            if (newContactNumber.equals(existingContactNumber)) {
                return true; // Email is already registered
            }
        }
        return false; // Email is not registered
    }

    // Generate a student ID in ascending order based on the current number of rows in the table
    public String generateStudentId() {
        // Iterate through student IDs until a unique one is found
        int nextStudentId = 1;

        while (isStudentAlreadyExists(String.valueOf(nextStudentId))) {
            nextStudentId++;
        }

        return String.valueOf(nextStudentId);
    }

    // Check if the student ID already exists in the table
    private boolean isStudentAlreadyExists(String studentId) {
        // Iterate through each row in the table model
        for (int row = 0; row < model.getRowCount(); row++) {
            // Check if the student ID in the current row matches the provided student ID
            if (model.getValueAt(row, 0).equals(studentId)) {
                // Return true if the student ID already exists
                return true;
            }
        }

        // If no match is found, return false
        return false;
    }

    public void delete() {
        // Prompt the user to enter the student ID for deletion
        String studentIdToDelete = JOptionPane.showInputDialog(null, "Enter Student ID to delete:");

        // Check if the entered student ID is not null and not empty
        if (studentIdToDelete != null && !studentIdToDelete.isEmpty()) {
            // Find the row index corresponding to the given student ID
            int rowToDelete = findRowByStudentId(studentIdToDelete);

            // Check if the student ID is found in the table
            if (rowToDelete != -1) {
                // Prompt the user to enter the admin password for confirmation
                JPasswordField passwordField = new JPasswordField();
                Object[] passwordPanel = {"Enter admin password:", passwordField};
                int confirmPassword = JOptionPane.showConfirmDialog(null, passwordPanel, "Admin Password", JOptionPane.OK_CANCEL_OPTION);

                if (confirmPassword == JOptionPane.OK_OPTION) {
                    String validateAdminPassword = new String(passwordField.getPassword());
                    // Continue with password validation and other logic
                    // Check if the entered admin password is correct
                    if (validateAdminPassword.equals(adminPassword)) {
                        // Confirm with the user before proceeding with deletion
                        int confirmDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                        // If the user confirms deletion, remove the row from the table model
                        if (confirmDelete == JOptionPane.YES_OPTION) {
                            model.removeRow(rowToDelete);
                            showInformationMessage("User deleted successfully.", "Deletion Success");
                        }
                    } else {
                        showErrorMessage("Admin password is incorrect.", "Unable to process request.");
                    }
                }

            } else {
                // Display an error message if the student ID is not found
                showErrorMessage("Student ID not found.", "Student Not Found");
            }
        }
    }

    // Find the row index based on the provided student ID in the table model
    private int findRowByStudentId(String studentId) {
        // Iterate through each row in the table model
        for (int row = 0; row < model.getRowCount(); row++) {
            // Check if the student ID in the current row matches the provided student ID
            if (model.getValueAt(row, 0).equals(studentId)) {
                // Return the index of the row if a match is found
                return row;
            }
        }

        // If no match is found, return -1
        return -1;
    }

    // This will run if the update button is triggered
    public void update() {
        // Prompt the user to enter the student ID for updating
        String studentIdToUpdate = JOptionPane.showInputDialog(null, "Enter Student ID to update:");
        if (studentIdToUpdate != null && !studentIdToUpdate.isEmpty()) {
            int rowToUpdate = findRowByStudentId(studentIdToUpdate);
            if (rowToUpdate != -1) {
                JPasswordField passwordField = new JPasswordField();
                Object[] passwordPanel = {"Enter admin password:", passwordField};
                int confirmPassword = JOptionPane.showConfirmDialog(null, passwordPanel, "Admin Password", JOptionPane.OK_CANCEL_OPTION);

                if (confirmPassword == JOptionPane.OK_OPTION) {
                    String adminPassword = new String(passwordField.getPassword());
                    // Continue with password validation and other logic
                    // Check if the entered admin password is correct
                    if (adminPassword.equals("1234")) {
                        // Confirm with the user before proceeding with deletion
                        int confirmUpdate = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                        // If the user confirms deletion, remove the row from the table model
                        if (confirmUpdate == JOptionPane.YES_OPTION) {
                            showUpdate(rowToUpdate);


                            showInformationMessage("User Update successfully.", "Updated Success");
                        }
                    } else {
                        showErrorMessage("Admin password is incorrect.", "Unable to process request.");
                    }
                }

            } else {
                showErrorMessage("Student ID not found.", "Student Not Found");
            }
        }
    }

    // Function to show a dialog for updating user information
    private void showUpdate(int rowToUpdate) {
        // Retrieve user information from the table model
        String fullName = (String) model.getValueAt(rowToUpdate, 1);
        Object course = model.getValueAt(rowToUpdate, 2);
        Object year = model.getValueAt(rowToUpdate, 3);
        String gender = (String) model.getValueAt(rowToUpdate, 4);
        String birthdate = (String) model.getValueAt(rowToUpdate, 5);
        String email = (String) model.getValueAt(rowToUpdate, 6);
        String contactNumber = (String) model.getValueAt(rowToUpdate, 7);
        String address = (String) model.getValueAt(rowToUpdate, 8);



        // Create a dialog for updating user information
        JDialog updateDialog = new JDialog((Frame) null, "Update User Information", true);
        updateDialog.setLayout(new GridLayout(10, 2));

        // Create components for the dialog
        JTextField fullNameField = new JTextField(fullName);

        // Create a combo box for the course
        String[] courses = {"Bachelor of Science in Information Technology", "Bachelor of Science in Computer Science" , "Bachelor of Science in Multimedia Studies"};
        JComboBox<String> courseField = new JComboBox<>(courses);
        courseField.setSelectedItem(course);

        // Create a combo box for the year
        String[] years = {"1", "2", "3", "4"};
        JComboBox<String> yearField = new JComboBox<>(years);
        yearField.setSelectedItem(year);

        // Create radio buttons for gender
        JRadioButton maleRadioButton = new JRadioButton("Male");
        JRadioButton femaleRadioButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        if (gender.equals("M")) {
            maleRadioButton.setSelected(true);
        } else if (gender.equals("F")) {
            femaleRadioButton.setSelected(true);
        }

        JTextField birthdateField = new JTextField(birthdate);
        JTextField emailField = new JTextField(email);
        JTextField contactNumberField = new JTextField(contactNumber);
        JTextField addressField = new JTextField(address);

        // Add components to the dialog
        updateDialog.add(new JLabel("Name:"));
        updateDialog.add(fullNameField);
        updateDialog.add(new JLabel("Course:"));
        updateDialog.add(courseField);
        updateDialog.add(new JLabel("Year Level:"));
        updateDialog.add(yearField);
        updateDialog.add(new JLabel("Gender:"));
        updateDialog.add(maleRadioButton);
        updateDialog.add(new JLabel(""));
        updateDialog.add(femaleRadioButton);
        updateDialog.add(new JLabel("Birthdate (mm/dd/yyyy):"));
        updateDialog.add(birthdateField);
        updateDialog.add(new JLabel("Email:"));
        updateDialog.add(emailField);
        updateDialog.add(new JLabel("Contact Number:"));
        updateDialog.add(contactNumberField);
        updateDialog.add(new JLabel("Address:"));
        updateDialog.add(addressField);

        // Create an "Update" button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate date format
                if (!isValidDateFormat(birthdateField.getText())) {
                    JOptionPane.showMessageDialog(updateDialog,
                            "Invalid date format. Please use MM/dd/yyyy.", "Date Format Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get selected gender
                String selectedGender = maleRadioButton.isSelected() ? "M" : (femaleRadioButton.isSelected() ? "F" : "");

                // Apply changes to the table model
                model.setValueAt(fullNameField.getText(), rowToUpdate, 1);
                model.setValueAt(courseField.getSelectedItem(), rowToUpdate, 2);
                model.setValueAt(yearField.getSelectedItem(), rowToUpdate, 3);
                model.setValueAt(selectedGender, rowToUpdate, 4);
                model.setValueAt(birthdateField.getText(), rowToUpdate, 5);
                model.setValueAt(emailField.getText(), rowToUpdate, 6);
                model.setValueAt(contactNumberField.getText(), rowToUpdate, 7);
                model.setValueAt(addressField.getText(), rowToUpdate, 8);

                // Close the dialog
                updateDialog.dispose();
            }
        });

        // Add the "Update" button to the dialog
        updateDialog.add(new JLabel(""));
        updateDialog.add(updateButton);

        // Set dialog properties
        updateDialog.setSize(400, 300);
        updateDialog.setLocationRelativeTo(null);
        updateDialog.setVisible(true);
    }



    // Clear all input fields when the clear button is triggered
    public void clearForm() {
        // Reset text fields to empty strings
        getFirstName().setText("");
        getMiddleInitial().setText("");
        getLastName().setText("");
        getBirthdate().setText("");
        getEmail().setText("");
        getContactNumber().setText("");
        getAddress().setText("");

        // Reset combo box selections to default
        getCourse().setSelectedIndex(0);
        getExtensionName().setSelectedIndex(0);
        getYearLevel().setSelectedIndex(0);

        // Reset radio button selections
        getMaleRadioButton().setSelected(false);
        getFemaleRadioButton().setSelected(false);
    }

    // Handle the search operation when the search button is triggered
    public void search() {
        // Get the student ID to search from the search text field
        String studentIdToSearch = getSearch().getText().trim();

        // Check if the search field is not empty
        if (!studentIdToSearch.isEmpty()) {
            // Find the row index based on the entered student ID
            int rowToView = findRowByStudentId(studentIdToSearch);

            // Check if the student ID is found in the table
            if (rowToView != -1) {
                // Display a dialog showing user information
                displayInformation(rowToView);
            } else {
                // Show an error message if the student ID is not found
                showErrorMessage("Student ID not found.", "Student Not Found");
            }
        } else {
            // Show an error message if the search field is empty
            showErrorMessage("Please enter a Student ID.", "Empty Field");
        }
    }


    // Display a dialog with user information based on the selected row
    public void displayInformation(int rowToView) {
        // Retrieve user information from the table model
        String studentId = (String) model.getValueAt(rowToView, 0);
        String fullName = (String) model.getValueAt(rowToView, 1);
        Object course = model.getValueAt(rowToView, 2);
        Object year = model.getValueAt(rowToView, 3);
        String gender = (String) model.getValueAt(rowToView, 4);
        String birthdate = (String) model.getValueAt(rowToView, 5);
        String email = (String) model.getValueAt(rowToView, 6);
        String contactNumber = (String) model.getValueAt(rowToView, 7);
        String address = (String) model.getValueAt(rowToView, 8);

        // Format user information for display
        String information = String.format("""
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            STUDENT ID                  : %s
            ────────────────────────────────────────────────────────
            NAME                             : %s
            ────────────────────────────────────────────────────────
            COURSE                        : %s
            ────────────────────────────────────────────────────────
            YEAR LEVEL                  : %s
            ────────────────────────────────────────────────────────
            GENDER                         : %s
            ────────────────────────────────────────────────────────
            DATE OF BIRTH            : %s
            ────────────────────────────────────────────────────────
            EMAIL                            : %s
            ────────────────────────────────────────────────────────
            CONTACT NUMBER    : %s
            ────────────────────────────────────────────────────────
            ADDRESS                     : %s
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            """, studentId, fullName, course, year, gender, birthdate, email, contactNumber, address);

        // Display the information in a dialog
        showInformationMessage(information, "Student Information");
    }


    // Sort the table based on the selected criteria
    public void sortTable() {
        // Get the selected sort criteria from the sortComboBox
        String sortCriteria = sortComboBox.getSelectedItem().toString();

        // Create a TableRowSorter for the table model
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);

        // Set the TableRowSorter to the studentsTable
        studentsTable.setRowSorter(sorter);

        // Create a list to store the sort keys
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        // Determine the column index based on the selected criteria
        switch (sortCriteria) {
            case "Student ID":
                sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
                break;
            case "Name":
                sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
                break;
            case "Course":
                sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
                break;
            case "Year Level":
                sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
                break;
            case "Gender":
                sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
                break;
            case "Address":
                sortKeys.add(new RowSorter.SortKey(7, SortOrder.ASCENDING));
                break;
            default:
                // Default to removing sort order if no valid criteria is selected
                sorter.setSortKeys(null);
                break;
        }

        // Set the sort keys for the TableRowSorter
        sorter.setSortKeys(sortKeys);
    }

}
