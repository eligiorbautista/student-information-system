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

        loginBtn.addActionListener(e -> onLogin());
        cancelBtn.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Set the icon
        ImageIcon icon = new ImageIcon("sims-icon.png");
        setIconImage(icon.getImage());
    }

    private void onLogin() {
        if (adminUsername.equals(usernameTextField.getText())
                && adminPassword.equals(new String(passwordTextField.getPassword()))) {
            openStudentInformationSystem();
        } else {
            showLoginError();
        }
    }

    private void openStudentInformationSystem() {
        StudentInformationSystemGUI dialog = new StudentInformationSystemGUI();
        dialog.setTitle("Student Information Management System");
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);
        dispose();
    }

    private void showLoginError() {
        JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
    }

    private void onCancel() {
        dispose();
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
