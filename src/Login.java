import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import bank.BankManagementSystem;

public class Login extends JFrame implements TextListener {
    private Container c;
    private JButton btnLogin, btnExit, btnSignup;
    private JLabel lblLogin, lblAcno, lblPass, lblStatus, lblSignup;
    private JLabel lblAcnoErr, lblPassErr;
    private TextField txtAcno, txtPass;

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private String sql = "";

    public Login(String aco) {
        connect(aco);
    }

    private void connect(String aco) {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bank_management_system", "root", "");

            conn.createStatement();

            if (conn != null)
                initFrame(aco);
        } catch (Exception e) {
            new Error404();
        }
    }

    private void initFrame(String aco) {
        boolean flag = true;
        c = getContentPane();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(400, 500);
        setLocationRelativeTo(null);
        setTitle("Login: BMS");

        try {
            stmt = conn.createStatement();
            sql = "SELECT * FROM `account_details` WHERE `logged_status` = '1'";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                new Main();
                flag = false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        if (flag) {
            GridBagConstraints gbc = new GridBagConstraints();

            lblLogin = new JLabel("Login");
            lblLogin.setFont(new Font("Cambria", Font.BOLD, 24));
            gbc.gridy = 0;
            gbc.gridx = 0;
            gbc.gridwidth = 4;
            gbc.insets = new Insets(10, 0, 30, 0);
            c.add(lblLogin, gbc);

            lblAcno = new JLabel("Account Number: ");
            gbc.gridy = 1;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.LINE_START;
            gbc.insets = new Insets(10, 10, 10, 10);
            c.add(lblAcno, gbc);

            txtAcno = new TextField(20);
            txtAcno.addTextListener(this);

            if (!aco.equals("0")) {
                txtAcno.setText(aco);
            }

            gbc.gridy = 1;
            gbc.gridx = 2;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(10, 10, 10, 0);
            c.add(txtAcno, gbc);

            lblAcnoErr = new JLabel(" ");
            lblAcnoErr.setForeground(Color.RED);
            gbc.gridy = 2;
            gbc.gridx = 0;
            gbc.gridwidth = 4;
            gbc.insets = new Insets(0, 0, 0, 0);
            c.add(lblAcnoErr, gbc);

            lblPass = new JLabel("Password");
            gbc.gridy = 3;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.LINE_START;
            c.add(lblPass, gbc);

            txtPass = new TextField(20);
            txtPass.setEchoChar('*');
            txtPass.addTextListener(this);

            if (!aco.equals("0")) {
                addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent we) {
                        txtPass.requestFocus();
                    }
                });
            }
            gbc.gridy = 3;
            gbc.gridx = 2;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(10, 10, 10, 0);
            c.add(txtPass, gbc);

            lblPassErr = new JLabel(" ");
            lblPassErr.setForeground(Color.RED);
            gbc.gridy = 4;
            gbc.gridx = 0;
            gbc.gridwidth = 4;
            gbc.insets = new Insets(0, 0, 0, 0);
            c.add(lblPassErr, gbc);

            lblStatus = new JLabel(" ");
            lblStatus.setForeground(Color.RED);
            gbc.gridy = 5;
            gbc.gridx = 0;
            gbc.gridwidth = 4;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(6, 0, 6, 0);
            c.add(lblStatus, gbc);

            btnLogin = new JButton("Login");
            btnLogin.setFont(new Font("Thoma", Font.BOLD, 14));
            btnLogin.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    login();
                }
            });
            gbc.gridy = 6;
            gbc.gridx = 0;
            gbc.gridwidth = 4;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.ipadx = 20;
            gbc.insets = new Insets(5, 0, 10, 0);
            c.add(btnLogin, gbc);

            lblSignup = new JLabel("Dont have an Account?", JLabel.RIGHT);
            gbc.gridy = 7;
            gbc.gridx = 1;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.LINE_END;
            c.add(lblSignup, gbc);

            btnSignup = new JButton("Create One");
            btnSignup.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    new Signup();
                    dispose();
                }
            });
            gbc.gridy = 7;
            gbc.gridx = 3;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(16, 0, 16, 0);
            c.add(btnSignup, gbc);

            btnExit = new JButton("Exit");
            btnExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    dispose();
                }
            });
            gbc.gridy = 8;
            gbc.gridx = 3;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
            gbc.insets = new Insets(3, 3, 3, 3);
            c.add(btnExit, gbc);

            setVisible(true);
        }
    }

    @Override
    public void textValueChanged(TextEvent te) {
        if (txtAcno.getText().isEmpty()) {
            lblAcnoErr.setText(" ");
        } else if (new BankManagementSystem().checkLong(txtAcno.getText()) == -1) {
            lblAcnoErr.setText("Account number must contain only digits");
        } else {
            lblAcnoErr.setText(" ");
        }

        if (!txtPass.getText().isEmpty()) {
            lblPassErr.setText(" ");
        }
    }

    private void login() {
        if (txtAcno.getText().isEmpty()) {
            lblAcnoErr.setText("Please fill this Field");
            return;
        }

        try {
            sql = "SELECT * FROM `account_details` WHERE `account_number` = '" + txtAcno.getText() + "'";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                if (txtPass.getText().isEmpty()) {
                    lblStatus.setText(" ");
                    lblPassErr.setText("Please fill these Field");
                    return;
                }

                if (rs.getString("password").equals(txtPass.getText())) {
                    lblStatus.setText(" ");

                    try {
                        sql = "UPDATE `account_details` SET `logged_status` = '1' WHERE `account_number` = '"
                                + txtAcno.getText() + "'";
                        stmt.executeUpdate(sql);

                        new Main();
                        dispose();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    lblStatus.setText("Password does not matched !");
                }
            } else {
                lblStatus.setText(txtAcno.getText() + " Not Found !");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
            new Login("0");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
