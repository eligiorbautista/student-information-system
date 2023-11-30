import javax.swing.*;
import java.awt.event.*;

public class LoginDialog extends JDialog {
    private JPanel contentPane;
    private JButton loginBtn;
    private JButton cancelBtn;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private String adminUsername = "admin";
    private String adminPassword = "1234";  
    public LoginDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(loginBtn);

        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onLogin();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onLogin() {
        // Validate username and password
        if (isValidCredentials(usernameTextField.getText(), new String(passwordTextField.getPassword()))) {
            // If valid, open the student information management interface
            StudentInformationSystemGUI dialog = new StudentInformationSystemGUI();
            dialog.setTitle("Student Information Management System");
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            System.exit(0);
            dispose();
        } else {
            // If invalid, show an error message
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        // Close the dialog
        dispose();
    }

    // Validate username and password
    private boolean isValidCredentials(String username, String password) {
        return adminUsername.equals(username) && adminPassword.equals(password);
    }

    public static void main(String[] args) {
        LoginDialog dialog = new LoginDialog();
        dialog.setTitle("Student Information Management System");
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);
    }
}
