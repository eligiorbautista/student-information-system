import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

public class StudentInformationSystemGUI extends JDialog {
    private JPanel contentPane;
    private JComboBox<String> sortComboBox;
    private JButton addBtn;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JTextField lastNameTextField;
    private JTextField firstNameTextField;
    private JTextField middleInitialTextField;
    private JComboBox<String> extensionNameComboBox;
    private JTextArea addressTextArea;
    private JButton clearBtn;
    private JButton searchBtn;
    private JComboBox<String> courseComboBox;
    private JTextField birthdateTextField;
    private JTextField emailTextField;
    private JTextField contactNumberTextField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JTextField searchTextField;
    private JComboBox<String> yearComboBox;
    private JTable studentsTable;
    private DefaultTableModel model;
    private ButtonGroup genderGroup;

    public StudentInformationSystemGUI() {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);

        ImageIcon icon = new ImageIcon("sims-icon.png");
        setIconImage(icon.getImage());

        initializeTable();
        initializeSampleData();
        setupListeners();
    }

    private void initializeTable() {
        model = new DefaultTableModel(null, new String[] { "Student ID", "Name", "Course", "Year Level", "Gender",
                "Date of Birth", "Email", "Contact Number", "Address", "Date of Registration" });
        studentsTable.setModel(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < model.getColumnCount(); i++) {
            studentsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void initializeSampleData() {
        model.addRow(new Object[] { "1", "Aquino, Albert", "Bachelor of Science in Information Technology", "1", 'M',
                "01/01/1991", "albertaquino@gmail.com", "09111111111", "Lucena City, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "2", "Brosas, Bea", "Bachelor of Science in Computer Science", "1", 'F',
                "02/02/1992", "beabrosas@gmail.com", "09222222222", "Tayabas City, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "3", "Caprino, Charlene", "Bachelor of Science in Computer Science", "2", 'F',
                "03/03/1993", "charlenecaprino@gmail.com", "09333333333", "Lucena City, Quezon Province",
                "12/02/2023" });
        model.addRow(new Object[] { "4", "Divala, Dion", "Bachelor of Science in Information Technology", "2", 'M',
                "04/04/1994", "diondivala@gmail.com", "09444444444", "Pagbilao, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "5", "Evaporada, Emily", "Bachelor of Science in Software Engineering", "3", 'F',
                "05/05/1995", "emilyevaporada@gmail.com", "09555555555", "Sariaya, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "6", "Fortillano, Frank", "Bachelor of Science in Mechanical Engineering", "4", 'M',
                "06/06/1996", "frankfortillano@gmail.com", "09666666666", "Tayabas, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "7", "Gomez, Grace", "Bachelor of Science in Electrical Engineering", "1", 'F',
                "07/07/1997", "gracegomez@gmail.com", "09777777777", "Atimonan, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "8", "Hernandez, Helena", "Bachelor of Science in Civil Engineering", "2", 'F',
                "08/08/1998", "helenahernandez@gmail.com", "09888888888", "Mulanay, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "9", "Ignacio, Isabella", "Bachelor of Business Administration (BBA)", "3", 'F',
                "09/09/1999", "isabellaignacio@gmail.com", "09999999999", "Pitogo, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "10", "Jimenez, Juan", "Bachelor of Science in Business Management", "4", 'M',
                "10/10/1991", "juanjimenez@gmail.com", "09234567898", "Macalelon, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "11", "Katigbak, Kacelyn", "Bachelor of Science in Accounting", "1", 'F',
                "11/11/1992", "kacelynkatigbak@gmail.com", "09234567899", "Tiaong, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "12", "Lopez, Lourdes", "Bachelor of Science in Biology", "2", 'F', "12/12/1993",
                "jacklopez@gmail.com", "09234567900", "Lucban, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "13", "Martinez, Melai", "Bachelor of Science in Chemistry", "3", 'F', "01/02/1993",
                "melaimartinez@gmail.com", "09234567901", "Lopez, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "14", "Natividad, Natalia", "Bachelor of Science in Physics", "4", 'F',
                "02/08/1994", "natalianatividad@gmail.com", "09234567902", "Mauban, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "15", "Ocampo, Octavio", "Bachelor of Science in Nursing", "1", 'M', "03/09/1995",
                "octavioocampo@gmail.com", "09234567903", "San Andres, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "16", "Pascual, Piolo", "Bachelor of Science in Pharmacy", "2", 'M', "04/16/1996",
                "piolopascual@gmail.com", "09234567904", "San Narciso, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "17", "Quintero, Quirino", "Bachelor of Science in Nutrition and Dietetics", 3, 'F',
                "05/12/1997", "quirinoquintero@gmail.com", "09234567905", "General Luna, Quezon Province",
                "12/02/2023" });
        model.addRow(new Object[] { "18", "Ramirez, Rosario", "Bachelor of Science in Mathematics", "4", 'F',
                "06/04/1998", "rosarioramirez@gmail.com", "09234567906", "Unisan, Quezon Province", "12/02/2023" });
        model.addRow(
                new Object[] { "19", "Santos, Santiago", "Bachelor of Science in Statistics", "1", 'M', "07/19/2000",
                        "santiagosantos@gmail.com", "09234567907", "Lucena City, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "20", "Tiu, Teresa", "Bachelor of Science in Computer Science", "3", 'F',
                "08/18/2001", "teresatiu@gmail.com", "09234567891", "Lucena City, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "21", "Uy, Uliver", "Bachelor of Science in Information Technology", "2", 'M',
                "09/02/2002", "uliveruy@gmail.com", "09234567892", "Pagbilao, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "22", "Valdez, Victoria", "Bachelor of Science in Electrical Engineering", "1", 'F',
                "07/12/2003", "victoriavaldez@gmail.com", "09234567895", "Atimonan, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "23", "Wishi, Washiyo", "Bachelor of Science in Mathematics", "4", 'F',
                "06/01/1998", "washiyowishi@gmail.com", "09234563906", "Unisan, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "24", "Xalve, Xion", "Bachelor of Science in Business Management", "4", 'M',
                "10/11/1991", "xionxalve@gmail.com", "09234567198", "Macalelon, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "25", "Yap, Ysabel", "Bachelor of Science in Civil Engineering", "2", 'F',
                "12/08/1997", "ysabelyap@gmail.com", "09234567896", "Mulanay, Quezon Province", "12/02/2023" });
        model.addRow(new Object[] { "26", "Zamora, Zed", "Bachelor of Business Administration (BBA)", "3", 'M',
                "04/16/2003", "zedzamora@gmail.com", "09234567897", "Pitogo, Quezon Province", "12/02/2023" });
    }

    private void setupListeners() {
        StudentManager manageStudent = new StudentManager(searchTextField, firstNameTextField, lastNameTextField,
                middleInitialTextField, extensionNameComboBox, courseComboBox, yearComboBox, maleRadioButton,
                femaleRadioButton, birthdateTextField, emailTextField, contactNumberTextField, addressTextArea,
                studentsTable, model, sortComboBox);

        addBtn.addActionListener(e -> {
            manageStudent.register();
        });
        updateBtn.addActionListener(e -> {
            manageStudent.update();
        });
        deleteBtn.addActionListener(e -> {
            manageStudent.delete();
        });
        clearBtn.addActionListener(e -> {
            manageStudent.clearForm();
        });
        searchBtn.addActionListener(e -> {
            manageStudent.search();
        });
        sortComboBox.addActionListener(e -> {
            manageStudent.sortTable();
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        StudentInformationSystemGUI dialog = new StudentInformationSystemGUI();
        dialog.setTitle("Student Information Management System");
        dialog.pack();
        dialog.setResizable(isDefaultLookAndFeelDecorated());
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);
    }
}
