import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentManager extends UserManager {
    // Get the current date
    LocalDate currentDate = LocalDate.now();

    // Create specific date format
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    // Format date to a String
    String registrationDate = currentDate.format(formatter).replaceAll("-", "/");




    private DefaultTableModel model;
    private JTable studentsTable;
    private JComboBox<String> sortComboBox;
    private JComboBox<String> course;
    private JComboBox<String> yearLevel;
    private String adminPassword = "1234";

    public StudentManager(
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
            JTextArea address,
            JTable studentsTable,
            DefaultTableModel model,
            JComboBox<String> sortComboBox) {
        super(search, firstName, lastName, middleInitial, extensionName, maleRadioButton,
                femaleRadioButton, birthdate, email, contactNumber, address);
        this.course = course;
        this.yearLevel = yearLevel;
        this.studentsTable = studentsTable;
        this.sortComboBox = sortComboBox;
        this.model = model;
    }

    protected JComboBox<String> getCourse() {
        return course;
    }

    protected JComboBox<String> getYearLevel() {
        return yearLevel;
    }

    @Override
    public void register() {
        // Extract user input
        String firstName = getFirstName().getText().replaceAll("\\s", "");
        String lastName = getLastName().getText().replaceAll("\\s", "");
        String middleInitial = getMiddleInitial().getText().toUpperCase().replaceAll("\\s", "");
        String extensionName = (getExtensionName().getSelectedIndex() > 0)
                ? getExtensionName().getSelectedItem().toString()
                : "";
        String gender = (getMaleRadioButton().isSelected()) ? "M" : (getFemaleRadioButton().isSelected()) ? "F" : "";
        String course = getCourse().getSelectedItem().toString();
        String year = getYearLevel().getSelectedItem().toString();
        String birthdate = getBirthdate().getText().replaceAll("\\s", "");
        String address = getAddress().getText();
        String email = getEmail().getText().replaceAll("\\s", "");
        String contactNumber = getContactNumber().getText().replaceAll("\\s", "");

        // Form Validation - textfields only
        boolean textFieldsNotEmpty = true;

        // Generate student ID
        String studentId = generateStudentId();

        // Validate non-blank textfields
        String[] inputFields = { firstName, lastName, gender, birthdate, address, email, contactNumber };
        for (String field : inputFields) {
            if (field.isEmpty()) {
                textFieldsNotEmpty = false;
                break;
            }
        }

        // Construct full name
        String fullName = String.format("%s %s %s.", capitalizeFirstAndLastName(lastName, firstName), extensionName,
                middleInitial);

        // Process if all fields are not empty and valid
        if (textFieldsNotEmpty) {
            if (isValidEmail(email)) {
                if (!isEmailRegistered(email)) {
                    if (isValidContactNumber(contactNumber)) {
                        if (!isContactNumberRegistered(contactNumber)) {
                            if (getCourse().getSelectedIndex() != 0) {
                                if (!gender.isEmpty()) {
                                    if (getYearLevel().getSelectedIndex() != 0) {
                                        if (isValidDateFormat(birthdate)) {
                                            // Add a new row to the table model
                                            model.addRow(new Object[] { studentId, fullName, course, year, gender,
                                                    birthdate, email, contactNumber.replace("+", ""), address,
                                                    registrationDate });
                                            showInformationMessage("User added successfully.", "Registration Success");
                                            clearForm();
                                        } else {
                                            showErrorMessage("Please enter a valid date in the format MM/DD/YYYY.",
                                                    "Invalid Date Format");
                                        }
                                    } else {
                                        showErrorMessage("Please select a year level.", "Invalid Year Level Selection");
                                    }
                                } else {
                                    showErrorMessage("Please select a gender.", "Gender Not Selected");
                                }
                            } else {
                                showErrorMessage("Please select a course.", "Invalid Course Selection");
                            }
                        } else {
                            showErrorMessage("Contact number is already registered.", "Duplicate Contact Number");
                        }
                    } else {
                        showErrorMessage("Please enter a valid contact number.", "Invalid Contact Number");
                    }
                } else {
                    showErrorMessage("Email is already registered.", "Duplicate Email");
                }
            } else {
                showErrorMessage("Please enter a valid email.", "Invalid Email");
            }
        } else {
            showErrorMessage("Please fill all the textfields.", "Unable to process request.");
        }
    }

    private boolean isEmailRegistered(String newEmail) {
        for (int row = 0; row < model.getRowCount(); row++) {
            String existingEmail = (String) model.getValueAt(row, 6);
            if (newEmail.equals(existingEmail)) {
                return true; // Email is already registered
            }
        }
        return false; // Email is not registered
    }

    private boolean isContactNumberRegistered(String newContactNumber) {
        for (int row = 0; row < model.getRowCount(); row++) {
            String existingContactNumber = (String) model.getValueAt(row, 7);
            if (newContactNumber.equals(existingContactNumber)) {
                return true; // Contact number is already registered
            }
        }
        return false; // Contact number is not registered
    }

    public String generateStudentId() {
        int nextStudentId = 1;

        while (isStudentIdAlreadyExists(String.valueOf(nextStudentId))) {
            nextStudentId++;
        }

        return String.valueOf(nextStudentId);
    }

    private boolean isStudentIdAlreadyExists(String studentId) {
        for (int row = 0; row < model.getRowCount(); row++) {
            if (model.getValueAt(row, 0).equals(studentId)) {
                // Return true if the student ID already exists
                return true;
            }
        }

        // If no match is found, return false
        return false;
    }

    @Override
    public void delete() {
        String studentIdToDelete = JOptionPane.showInputDialog(null, "Enter Student ID to delete:");

        if (studentIdToDelete != null && !studentIdToDelete.isEmpty()) {
            int rowToDelete = findRowByStudentId(studentIdToDelete);

            if (rowToDelete != -1) {
                JPasswordField passwordField = new JPasswordField();
                Object[] passwordPanel = { "Enter admin password:", passwordField };
                int confirmPassword = JOptionPane.showConfirmDialog(null, passwordPanel, "Admin Password",
                        JOptionPane.OK_CANCEL_OPTION);

                if (confirmPassword == JOptionPane.OK_OPTION) {
                    String validateAdminPassword = new String(passwordField.getPassword());

                    if (validateAdminPassword.equals(adminPassword)) {
                        int confirmDelete = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete this user?", "Confirm Deletion",
                                JOptionPane.YES_NO_OPTION);

                        if (confirmDelete == JOptionPane.YES_OPTION) {
                            model.removeRow(rowToDelete);
                            showInformationMessage("User deleted successfully.", "Deletion Success");
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

    private int findRowByStudentId(String studentId) {
        for (int row = 0; row < model.getRowCount(); row++) {
            if (model.getValueAt(row, 0).equals(studentId)) {
                // Return the index of the row if a match is found
                return row;
            }
        }
        // If no match is found, return -1
        return -1;
    }

    @Override
    public void update() {
        String studentIdToUpdate = JOptionPane.showInputDialog(null, "Enter Student ID to update:");
        if (studentIdToUpdate != null && !studentIdToUpdate.isEmpty()) {
            int rowToUpdate = findRowByStudentId(studentIdToUpdate);
            if (rowToUpdate != -1) {
                JPasswordField passwordField = new JPasswordField();
                Object[] passwordPanel = { "Enter admin password:", passwordField };
                int confirmPassword = JOptionPane.showConfirmDialog(null, passwordPanel, "Admin Password",
                        JOptionPane.OK_CANCEL_OPTION);

                if (confirmPassword == JOptionPane.OK_OPTION) {
                    String adminPassword = new String(passwordField.getPassword());
                    if (adminPassword.equals("1234")) {
                        showUpdate(rowToUpdate);
                    } else {
                        showErrorMessage("Admin password is incorrect.", "Unable to process request.");
                    }
                }
            } else {
                showErrorMessage("Student ID not found.", "Student Not Found");
            }
        }
    }

    private void showUpdate(int rowToUpdate) {
        String fullName = (String) model.getValueAt(rowToUpdate, 1);
        Object course = model.getValueAt(rowToUpdate, 2);
        Object year = model.getValueAt(rowToUpdate, 3);
        String gender = model.getValueAt(rowToUpdate, 4).toString();
        String birthdate = (String) model.getValueAt(rowToUpdate, 5);
        String email = (String) model.getValueAt(rowToUpdate, 6);
        String contactNumber = (String) model.getValueAt(rowToUpdate, 7);
        String address = (String) model.getValueAt(rowToUpdate, 8);

        JDialog updateDialog = new JDialog((Frame) null, "Update User Information", true);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        updateDialog.setContentPane(contentPane);
 
        ImageIcon icon = new ImageIcon("sims-icon.png");
        updateDialog.setIconImage(icon.getImage());

        updateDialog.setLayout(new GridLayout(10, 2));  

        JTextField updateFullName = new JTextField(fullName);

        String[] courses = {
                "Select course",
                "Bachelor of Science in Computer Science",
                "Bachelor of Science in Information Technology",
                "Bachelor of Science in Software Engineering",
                "Bachelor of Science in Mechanical Engineering",
                "Bachelor of Science in Electrical Engineering",
                "Bachelor of Science in Civil Engineering",
                "Bachelor of Business Administration",
                "Bachelor of Science in Business Management",
                "Bachelor of Science in Accounting",
                "Bachelor of Science in Biology",
                "Bachelor of Science in Chemistry",
                "Bachelor of Science in Physics",
                "Bachelor of Science in Nursing",
                "Bachelor of Science in Pharmacy",
                "Bachelor of Science in Nutrition and Dietetics",
                "Bachelor of Science in Mathematics",
                "Bachelor of Science in Statistics",
                "Bachelor of Science in Actuarial Science",
                "Bachelor of Arts in Communication",
                "Bachelor of Arts in Journalism",
                "Bachelor of Arts in Public Relations",
                "Bachelor of Arts in Psychology",
                "Bachelor of Arts in Sociology",
                "Bachelor of Arts in Political Science",
                "Bachelor of Fine Arts in Visual Arts",
                "Bachelor of Fine Arts in Graphic Design",
                "Bachelor of Fine Arts in Performing Arts",
                "Bachelor of Science in Elementary Education",
                "Bachelor of Science in Secondary Education",
                "Bachelor of Science in Special Education"
        };
        JComboBox<String> updateCourse = new JComboBox<>(courses);
        updateCourse.setSelectedItem(course);

        String[] years = { "Select year level", "1", "2", "3", "4", "5" };
        JComboBox<String> updateYear = new JComboBox<>(years);
        updateYear.setSelectedItem(year);

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

        JTextField updateBirthdate = new JTextField(birthdate);
        JTextField updateEmail = new JTextField(email);
        JTextField updateContactNumber = new JTextField(contactNumber);
        JTextField updateAddress = new JTextField(address);

        updateDialog.add(new JLabel("Name:"));
        updateDialog.add(updateFullName);
        updateDialog.add(new JLabel("Course:"));
        updateDialog.add(updateCourse);
        updateDialog.add(new JLabel("Year Level:"));
        updateDialog.add(updateYear);
        updateDialog.add(new JLabel("Gender:"));
        updateDialog.add(maleRadioButton);
        updateDialog.add(new JLabel(""));
        updateDialog.add(femaleRadioButton);
        updateDialog.add(new JLabel("Birthdate (mm/dd/yyyy):"));
        updateDialog.add(updateBirthdate);
        updateDialog.add(new JLabel("Email:"));
        updateDialog.add(updateEmail);
        updateDialog.add(new JLabel("Contact Number:"));
        updateDialog.add(updateContactNumber);
        updateDialog.add(new JLabel("Address:"));
        updateDialog.add(updateAddress);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            if (!isValidDateFormat(updateBirthdate.getText())) {
                JOptionPane.showMessageDialog(updateDialog, "Invalid date format. Please use MM/dd/yyyy.",
                        "Date Format Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String updateGender = maleRadioButton.isSelected() ? "M" : (femaleRadioButton.isSelected() ? "F" : "");

            boolean textFieldsNotEmpty = true;
            String[] inputFields = { updateFullName.getText(), updateEmail.getText(), updateContactNumber.getText(),
                    updateAddress.getText(), updateBirthdate.getText() };

            for (String field : inputFields) {
                if (field.isEmpty()) {
                    textFieldsNotEmpty = false;
                    break;
                }
            }

            if (textFieldsNotEmpty) {
                if (isValidEmail(updateEmail.getText())) {
                    if (model.getValueAt(rowToUpdate, 6) == updateEmail
                            .getText() == !isEmailRegistered(updateEmail.getText())) {
                        if (isValidContactNumber(updateContactNumber.getText())) {
                            if (model.getValueAt(rowToUpdate, 7) == updateContactNumber
                                    .getText() == !isContactNumberRegistered(updateContactNumber.getText())) {
                                if (updateCourse.getSelectedIndex() != 0) {
                                    if (!updateGender.isEmpty()) {
                                        if (updateYear.getSelectedIndex() != 0) {
                                            if (isValidDateFormat(updateBirthdate.getText())) {
                                                int confirmUpdate = JOptionPane.showConfirmDialog(null,
                                                        "Are you sure you want to update this user?",
                                                        "Confirm Deletion",
                                                        JOptionPane.YES_NO_OPTION);

                                                if (confirmUpdate == JOptionPane.YES_OPTION) {
                                                    model.setValueAt(updateFullName.getText(), rowToUpdate, 1);
                                                    model.setValueAt(updateCourse.getSelectedItem(), rowToUpdate, 2);
                                                    model.setValueAt(updateYear.getSelectedItem(), rowToUpdate, 3);
                                                    model.setValueAt(updateGender, rowToUpdate, 4);
                                                    model.setValueAt(updateBirthdate.getText(), rowToUpdate, 5);
                                                    model.setValueAt(updateEmail.getText(), rowToUpdate, 6);
                                                    model.setValueAt(updateContactNumber.getText(), rowToUpdate, 7);
                                                    model.setValueAt(updateAddress.getText(), rowToUpdate, 8);

                                                    updateDialog.dispose();
                                                    showInformationMessage("User Update successfully.",
                                                            "Updated Success");
                                                }
                                            } else {
                                                showErrorMessage("Please enter a valid date in the format MM/DD/YYYY.",
                                                        "Invalid Date Format");
                                            }
                                        } else {
                                            showErrorMessage("Please select a year level.",
                                                    "Invalid Year Level Selection");
                                        }
                                    } else {
                                        showErrorMessage("Please select a gender.", "Gender Not Selected");
                                    }
                                } else {
                                    showErrorMessage("Please select a course.", "Invalid Course Selection");
                                }
                            } else {
                                showErrorMessage("Contact number is already registered.", "Duplicate Contact Number");
                            }
                        } else {
                            showErrorMessage(
                                    "Please enter a valid contact number.\n\nE.g\n0928 592 8274\n63 928 5928 274",
                                    "Invalid Contact Number");
                        }
                    } else {
                        showErrorMessage("Email is already registered.", "Duplicate Email");
                    }
                } else {
                    showErrorMessage("Please enter a valid email.\n\nE.g\njuandelacruz@example.com", "Invalid Email");
                }
            } else {
                showErrorMessage("Please fill all the textfields.", "Unable to process request.");
            }
        });

        updateDialog.add(new JLabel(""));
        updateDialog.add(updateButton);

        updateDialog.setSize(600, 250);
        updateDialog.setLocationRelativeTo(null);
        updateDialog.setVisible(true);
    }

    @Override
    public void clearForm() {
        getFirstName().setText("");
        getMiddleInitial().setText("");
        getLastName().setText("");
        getBirthdate().setText("");
        getEmail().setText("");
        getContactNumber().setText("");
        getAddress().setText("");

        getCourse().setSelectedIndex(0);
        getExtensionName().setSelectedIndex(0);
        getYearLevel().setSelectedIndex(0);

        getMaleRadioButton().setSelected(false);
        getFemaleRadioButton().setSelected(false);
    }

    @Override
    public void search() {
        String studentIdToSearch = getSearch().getText().trim();

        if (!studentIdToSearch.isEmpty()) {
            int rowToView = findRowByStudentId(studentIdToSearch);

            if (rowToView != -1) {
                displayInformation(rowToView);
            } else {
                showErrorMessage("Student ID not found.", "Student Not Found");
            }
        } else {
            showErrorMessage("Please enter a Student ID.", "Empty Field");
        }
    }

    @Override
    public void displayInformation(int rowToView) {
        String studentId = (String) model.getValueAt(rowToView, 0);
        String fullName = (String) model.getValueAt(rowToView, 1);
        Object course = model.getValueAt(rowToView, 2);
        Object year = model.getValueAt(rowToView, 3);
        String gender = model.getValueAt(rowToView, 4).toString();
        String birthdate = (String) model.getValueAt(rowToView, 5);
        String email = (String) model.getValueAt(rowToView, 6);
        String contactNumber = (String) model.getValueAt(rowToView, 7);
        String address = (String) model.getValueAt(rowToView, 8);
        String registrationDate = (String) model.getValueAt(rowToView, 9);

        String information = String.format("""
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                 STUDENT ID                            : %s
                ────────────────────────────────────────────────────────────────────────
                 NAME                                       : %s
                ────────────────────────────────────────────────────────────────────────
                 COURSE                                  : %s
                ────────────────────────────────────────────────────────────────────────
                 YEAR LEVEL                            : %s
                ────────────────────────────────────────────────────────────────────────
                 GENDER                                   : %s
                ────────────────────────────────────────────────────────────────────────
                 DATE OF BIRTH                      : %s
                ────────────────────────────────────────────────────────────────────────
                 EMAIL                                      : %s
                ────────────────────────────────────────────────────────────────────────
                 CONTACT NUMBER              : %s
                ────────────────────────────────────────────────────────────────────────
                 ADDRESS                               : %s
                ────────────────────────────────────────────────────────────────────────
                 DATE OF REGISTRATION     : %s
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                """, studentId, fullName, course, year, gender, birthdate, email, contactNumber, address,
                registrationDate);

        showInformationMessage(information, "Student Information");
    }

    @Override
    public void sortTable() {
        String sortCriteria = sortComboBox.getSelectedItem().toString();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        studentsTable.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        switch (sortCriteria) {
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
                sorter.setSortKeys(null);
                break;
        }

        sorter.setSortKeys(sortKeys);
    }

}
