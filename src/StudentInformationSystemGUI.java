import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;


public class StudentInformationSystemGUI extends JDialog {
    private JPanel contentPane;
    private JComboBox sortComboBox;
    private JButton addBtn;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JTable studentsTable;
    private JTextField lastNameTextField;
    private JTextField firstNameTextField;
    private JTextField middleInitialTextField;
    private JComboBox extensionNameComboBox;
    private JTextArea addressTextArea;
    private JButton clearBtn;
    private JButton searchBtn;
    private JComboBox courseComboBox;
    private JTextField birthdateTextField;
    private JTextField emailTextField;
    private JTextField contactNumberTextField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JTextField searchTextField;
    private JComboBox yearComboBox;
    private ButtonGroup genderGroup;
    private DefaultTableModel model;

    public StudentInformationSystemGUI() {
        initializeComponents();
        initializeTable();
        initializeData();
        setupListeners();
    }

    private void initializeComponents() {
        setContentPane(contentPane);
        setModal(true);
    }

    private void initializeTable() {
        model = new DefaultTableModel(null, new String[]{"Student ID", "Name", "Course", "Year Level", "Gender", "Date of Birth", "Email", "Contact Number", "Address"});
        studentsTable.setModel(model);
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < model.getColumnCount(); i++) {
            studentsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void initializeData() {
        model.addRow(new Object[]{1, "Aquino, Albert", "Bachelor of Science in Information Technology", 1, 'M', "01/01/1991", "albertaquino@gmail.com", "09111111111", "Lucena City, Quezon Province"});
        model.addRow(new Object[]{1, "Brosas, Bea", "Bachelor of Science in Computer Science", 1, 'F', "02/02/1992", "beabrosas@gmail.com", "09222222222", "Tayabas City, Quezon Province"});
    }


    private void setupListeners() {
        StudentManager manageStudent = new StudentManager(searchTextField ,firstNameTextField, lastNameTextField, middleInitialTextField, extensionNameComboBox, courseComboBox, yearComboBox, maleRadioButton,femaleRadioButton, birthdateTextField, emailTextField, contactNumberTextField, addressTextArea, studentsTable,model, sortComboBox);

        // Register new student
        addBtn.addActionListener(e -> manageStudent.register());
        updateBtn.addActionListener(e -> manageStudent.update());
        deleteBtn.addActionListener(e -> manageStudent.delete());
        clearBtn.addActionListener(e -> manageStudent.clearForm());
        searchBtn.addActionListener(e -> manageStudent.search());
        sortComboBox.addActionListener(e -> manageStudent.sortTable());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        StudentInformationSystemGUI dialog = new StudentInformationSystemGUI();
        dialog.setTitle("Student Information Management System");
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);
    }

}