import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import bank.BankManagementSystem;

import java.sql.*;

public class ChangePass extends JFrame implements TextListener {
    private Container c;
    private JButton btnGoBack, btnChangePass, btnCheck;
    private JCheckBox cbShowOldPass, cbShowNewPass, cbShowConfirmPass;
    private JLabel lblOldPass, lblNewPass, lblConfirmPass, lblStatus;
    private JLabel lblOldPassErr, lblNewPassErr, lblConfirmPassErr;
    private TextField txtOldPass, txtNewPass, txtConfirmPass;
    private Panel s;

    private Connection conn;
    private Statement stmt;
    private String sql;
    private ResultSet rs;

    public ChangePass() {
        connect();
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bank_management_system", "root", "");
            conn.createStatement();

            if (conn != null)
                initFrame();
        } catch (Exception e) {
            new Error404();
        }
    }

    private void initFrame() {
        c = getContentPane();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(400, 500);
        setLocationRelativeTo(null);
        setTitle("Change Password : BMS");

        GridBagConstraints gbc = new GridBagConstraints();

        lblOldPass = new JLabel("Old Password: ");
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblOldPass, gbc);

        txtOldPass = new TextField(20);
        txtOldPass.setEchoChar('*');
        txtOldPass.addTextListener(this);
        gbc.gridy = 0;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtOldPass, gbc);

        lblOldPassErr = new JLabel(" ");
        lblOldPassErr.setForeground(Color.RED);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblOldPassErr, gbc);

        cbShowOldPass = new JCheckBox("Show Password");
        cbShowOldPass.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                if (cbShowOldPass.isSelected()) {
                    cbShowOldPass.setText("Hide Password");
                    txtOldPass.setEchoChar((char) 0);
                } else {
                    cbShowOldPass.setText("Show Password");
                    txtOldPass.setEchoChar('*');
                }
            }
        });
        gbc.gridy = 2;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        c.add(cbShowOldPass, gbc);

        btnCheck = new JButton("Check Old Pass");
        btnCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                checkPass();
            }
        });
        gbc.gridy = 3;
        gbc.gridx = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
        c.add(btnCheck, gbc);

        s = new Panel(new BorderLayout());
        s.add(new JSeparator(), BorderLayout.NORTH);
        s.add(new JSeparator(), BorderLayout.SOUTH);
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(s, gbc);

        lblNewPass = new JLabel("New Password: ");
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblNewPass, gbc);

        txtNewPass = new TextField(20);
        txtNewPass.setEchoChar('*');
        txtNewPass.setEditable(false);
        txtNewPass.addTextListener(this);
        gbc.gridy = 5;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtNewPass, gbc);

        lblNewPassErr = new JLabel(" ");
        lblNewPassErr.setForeground(Color.RED);
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblNewPassErr, gbc);

        cbShowNewPass = new JCheckBox("Show Password");
        cbShowNewPass.setEnabled(false);
        cbShowNewPass.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                if (cbShowNewPass.isSelected()) {
                    cbShowNewPass.setText("Hide Password");
                    txtNewPass.setEchoChar((char) 0);
                } else {
                    cbShowNewPass.setText("Show Password");
                    txtNewPass.setEchoChar('*');
                }
            }
        });
        gbc.gridy = 7;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        c.add(cbShowNewPass, gbc);

        lblConfirmPass = new JLabel("Confirm Password: ");
        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblConfirmPass, gbc);

        txtConfirmPass = new TextField(20);
        txtConfirmPass.setEchoChar('*');
        txtConfirmPass.setEditable(false);
        txtConfirmPass.addTextListener(this);
        gbc.gridy = 8;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtConfirmPass, gbc);

        lblConfirmPassErr = new JLabel(" ");
        lblConfirmPassErr.setForeground(Color.RED);
        gbc.gridy = 9;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblConfirmPassErr, gbc);

        cbShowConfirmPass = new JCheckBox("Show Password");
        cbShowConfirmPass.setEnabled(false);
        cbShowConfirmPass.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                if (cbShowConfirmPass.isSelected()) {
                    cbShowConfirmPass.setText("Hide Password");
                    txtConfirmPass.setEchoChar((char) 0);
                } else {
                    cbShowConfirmPass.setText("Show Password");
                    txtConfirmPass.setEchoChar('*');
                }
            }
        });
        gbc.gridy = 10;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        c.add(cbShowConfirmPass, gbc);

        btnChangePass = new JButton("Change Password");
        btnChangePass.setEnabled(false);
        btnChangePass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                changePass();
            }
        });
        gbc.gridy = 11;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(20, 10, 10, 10);
        c.add(btnChangePass, gbc);

        lblStatus = new JLabel(" ");
        gbc.gridy = 12;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblStatus, gbc);

        btnGoBack = new JButton("Go Back");
        btnGoBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Main();
                dispose();
            }
        });
        gbc.gridy = 13;
        gbc.gridx = 3;
        gbc.gridwidth = 2;
        gbc.ipadx = 0;
        gbc.insets = new Insets(3, 3, 3, 0);
        gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
        c.add(btnGoBack, gbc);

        setVisible(true);
    }

    @Override
    public void textValueChanged(TextEvent arg0) {
        if (!btnChangePass.isEnabled()) {
            if (txtOldPass.getText().isEmpty()) {
                lblOldPassErr.setText(" ");
            } else {
                lblOldPassErr.setText(" ");
            }
        } else {
            if (txtNewPass.getText().isEmpty()) {
                lblNewPassErr.setText(" ");
            } else if (txtNewPass.getText().length() < 3) {
                lblNewPassErr.setText("Password length must be greater than 3");
            } else if (txtNewPass.getText().length() > 15) {
                lblNewPassErr.setText("Password length must be less than 15");
            } else if (txtNewPass.getText().equals(txtNewPass.getText().toLowerCase())) {
                lblNewPassErr.setText("Password must contain at least 1 Capital Letter");
            } else if (txtNewPass.getText().equals(txtNewPass.getText().toUpperCase())) {
                lblNewPassErr.setText("Password must contain at least 1 Capital Letter");
            } else if (!new BankManagementSystem().isDigitPresent(txtNewPass.getText())) {
                lblNewPassErr.setText("Password must contain at least 1 Digit");
            } else {
                lblNewPassErr.setText(" ");
            }

            if (txtConfirmPass.getText().isEmpty()) {
                lblConfirmPassErr.setText(" ");
            } else if (!txtConfirmPass.getText().equals(txtNewPass.getText())) {
                lblConfirmPassErr.setText("Password Not Matched !!");
            } else {
                lblConfirmPassErr.setText(" ");
            }
        }
    }

    private void checkPass() {
        if (txtOldPass.getText().isEmpty()) {
            lblOldPassErr.setText("Please fill this field !");
            return;
        }

        try {
            stmt = conn.createStatement();

            sql = "SELECT * FROM `account_details` WHERE `logged_status` = '1'";
            rs = stmt.executeQuery(sql);
            rs.next();

            if (txtOldPass.getText().equals(rs.getString("password"))) {
                lblStatus.setText(" ");
                btnChangePass.setEnabled(true);
                txtNewPass.setEditable(true);
                txtConfirmPass.setEditable(true);
                cbShowNewPass.setEnabled(true);
                cbShowConfirmPass.setEnabled(true);
            } else {
                lblStatus.setForeground(Color.RED);
                lblStatus.setText("Wrong Password Entered !");
                btnChangePass.setEnabled(false);
                txtNewPass.setEditable(false);
                txtConfirmPass.setEditable(false);
                cbShowNewPass.setEnabled(false);
                cbShowConfirmPass.setEnabled(false);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void changePass() {
        boolean returnFlag = false;

        if (txtNewPass.getText().isEmpty()) {
            lblNewPassErr.setText("Please fill this field !");
            returnFlag = true;
        }
        if (txtConfirmPass.getText().isEmpty()) {
            lblConfirmPassErr.setText("Please fill this field !");
            returnFlag = true;
        }

        if (!(lblNewPassErr.getText().equals(" ") && lblConfirmPassErr.getText().equals(" "))) {
            returnFlag = true;
        }

        if (returnFlag) {
            return;
        }

        try {
            sql = "UPDATE `account_details` SET `password` = '" + txtNewPass.getText() + "' WHERE `account_number` = '"
                    + rs.getString("account_number") + "'";
            if (stmt.executeUpdate(sql) == 1) {
                System.out.println("Updated");

                lblStatus.setText("Password Updated !!");
                txtOldPass.setText("");
                txtNewPass.setText("");
                txtNewPass.setEditable(false);
                txtConfirmPass.setText("");
                txtConfirmPass.setEditable(false);
                cbShowOldPass.setSelected(false);
                cbShowOldPass.setText("Show Password");
                cbShowOldPass.setEnabled(false);
                cbShowNewPass.setSelected(false);
                cbShowNewPass.setText("Show Password");
                cbShowNewPass.setEnabled(false);
                cbShowConfirmPass.setSelected(false);
                cbShowConfirmPass.setText("Show Password");
                cbShowConfirmPass.setEnabled(false);
                btnChangePass.setEnabled(false);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            new ChangePass();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}