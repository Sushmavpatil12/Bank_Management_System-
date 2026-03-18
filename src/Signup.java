import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import bank.BankManagementSystem;

public class Signup extends JFrame implements TextListener {
    private Container c;
    private JButton btnSignup, btnGoBack;
    private JLabel lblSignup, lblName, lblAccountType, lblBalance, lblAge, lblOccupation, lblPass, lblStatus;
    private JLabel lblNameErr, lblBalanceErr, lblOccupationErr, lblAgeErr, lblPassErr;
    private TextField txtName, txtBalance, txtAge, txtOccupation, txtPass;
    private ButtonGroup bgAccountType;
    private JRadioButton rbSaving, rbCurrent;

    private Connection conn;
    private Statement stmt;
    private String sql;

    public Signup() {
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
        setTitle("Signup: BMS");

        GridBagConstraints gbc = new GridBagConstraints();

        lblSignup = new JLabel("Signup");
        lblSignup.setFont(new Font("Cambria", Font.BOLD, 24));
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(5, 0, 5, 0);
        c.add(lblSignup, gbc);

        lblName = new JLabel("Name: ");
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 10);
        c.add(lblName, gbc);

        txtName = new TextField(20);
        txtName.addTextListener(this);
        gbc.gridy = 1;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtName, gbc);

        lblNameErr = new JLabel(" ");
        lblNameErr.setForeground(Color.RED);
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblNameErr, gbc);

        lblAccountType = new JLabel("Account Type: ");
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        c.add(lblAccountType, gbc);

        rbSaving = new JRadioButton("Saving", true);
        gbc.gridy = 3;
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        c.add(rbSaving, gbc);

        rbCurrent = new JRadioButton("Current", false);
        gbc.gridy = 3;
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        c.add(rbCurrent, gbc);

        bgAccountType = new ButtonGroup();
        bgAccountType.add(rbSaving);
        bgAccountType.add(rbCurrent);

        lblBalance = new JLabel("Balance: ");
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 10);
        c.add(lblBalance, gbc);

        txtBalance = new TextField(20);
        txtBalance.addTextListener(this);
        gbc.gridy = 4;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtBalance, gbc);

        lblBalanceErr = new JLabel(" ");
        lblBalanceErr.setForeground(Color.RED);
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblBalanceErr, gbc);

        lblOccupation = new JLabel("Occupation: ");
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 10);
        c.add(lblOccupation, gbc);

        txtOccupation = new TextField(20);
        txtOccupation.addTextListener(this);
        gbc.gridy = 6;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtOccupation, gbc);

        lblOccupationErr = new JLabel(" ");
        lblOccupationErr.setForeground(Color.RED);
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblOccupationErr, gbc);

        lblAge = new JLabel("Age: ");
        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 10);
        c.add(lblAge, gbc);

        txtAge = new TextField(20);
        txtAge.addTextListener(this);
        gbc.gridy = 8;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtAge, gbc);

        lblAgeErr = new JLabel(" ");
        lblAgeErr.setForeground(Color.RED);
        gbc.gridy = 9;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblAgeErr, gbc);

        lblPass = new JLabel("Password: ");
        gbc.gridy = 10;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 10);
        c.add(lblPass, gbc);

        txtPass = new TextField(20);
        txtPass.addTextListener(this);
        gbc.gridy = 10;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtPass, gbc);

        lblPassErr = new JLabel(" ");
        lblPassErr.setForeground(Color.RED);
        gbc.gridy = 11;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblPassErr, gbc);

        btnSignup = new JButton("Signup");
        btnSignup.setFont(new Font("Thoma", Font.BOLD, 14));
        btnSignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                signUp();
            }
        });
        gbc.gridy = 12;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipadx = 20;
        gbc.insets = new Insets(16, 0, 10, 0);
        c.add(btnSignup, gbc);

        lblStatus = new JLabel("");
        lblStatus.setForeground(Color.RED);
        gbc.gridy = 13;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(6, 0, 6, 0);
        c.add(lblStatus, gbc);

        btnGoBack = new JButton("Go Back");
        btnGoBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Login("0");
                dispose();
            }
        });
        gbc.gridy = 13;
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
        gbc.insets = new Insets(3, 3, 3, 0);
        c.add(btnGoBack, gbc);

        setVisible(true);
    }

    @Override
    public void textValueChanged(TextEvent te) {
        if (txtName.getText().isEmpty()) {
            lblNameErr.setText(" ");
        } else if (new BankManagementSystem().isDigitPresent(txtName.getText())) {
            lblNameErr.setText("Name cannot contain digits");
        } else {
            lblNameErr.setText(" ");
        }

        if (txtBalance.getText().isEmpty()) {
            lblBalanceErr.setText(" ");
        } else if (new BankManagementSystem().checkLong(txtBalance.getText()) == -1) {
            lblBalanceErr.setText("Digits must be entered");
        } else {
            lblBalanceErr.setText(" ");
        }

        if (txtOccupation.getText().isEmpty()) {
            lblOccupationErr.setText(" ");
        } else if (new BankManagementSystem().isDigitPresent(txtOccupation.getText())) {
            lblOccupationErr.setText("Job cannot contain digits");
        } else {
            lblOccupationErr.setText(" ");
        }

        if (txtAge.getText().isEmpty()) {
            lblAgeErr.setText(" ");
        } else if (new BankManagementSystem().checkLong(txtAge.getText()) == -1) {
            lblAgeErr.setText("Age must be a number");
        } else if (Integer.parseInt(txtAge.getText()) <= 0 || Integer.parseInt(txtAge.getText()) > 120) {
            lblAgeErr.setText("Invalid age inserted");
        } else {
            lblAgeErr.setText(" ");
        }

        if (txtPass.getText().isEmpty()) {
            lblPassErr.setText(" ");
        } else if (txtPass.getText().length() < 3) {
            lblPassErr.setText("Password length must be greater than 3");
        } else if (txtPass.getText().length() > 10) {
            lblPassErr.setText("Password length must be less than 10");
        } else if (txtPass.getText().equals(txtPass.getText().toLowerCase())) {
            lblPassErr.setText("Password must contain at least 1 Capital Letter");
        } else if (!new BankManagementSystem().isDigitPresent(txtPass.getText())) {
            lblPassErr.setText("Password must contain at least 1 Digit");
        } else {
            lblPassErr.setText(" ");
        }
    }

    private void signUp() {
        boolean returnFlag = false;

        if (txtName.getText().isEmpty()) {
            lblNameErr.setText("Please fill this Field");
            returnFlag = true;
        }

        if (txtBalance.getText().isEmpty()) {
            lblBalanceErr.setText("Please fill this Field");
            returnFlag = true;
        }

        if (txtOccupation.getText().isEmpty()) {
            lblOccupationErr.setText("Please fill this Field");
            returnFlag = true;
        }

        if (txtAge.getText().isEmpty()) {
            lblAgeErr.setText("Please fill this Field");
            returnFlag = true;
        }

        if (txtPass.getText().isEmpty()) {
            lblPassErr.setText("Please fill this Field");
            returnFlag = true;
        }

        if (!(lblAgeErr.getText().equals(" ") && lblBalanceErr.getText().equals(" ")
                && lblOccupationErr.getText().equals(" ") && lblNameErr.getText().equals(" ")
                && lblPassErr.getText().equals(" "))) {
            returnFlag = true;
        }

        if (returnFlag) {
            return;
        }

        String name = txtName.getText();
        String occupation = txtOccupation.getText();
        String password = txtPass.getText();
        String accountType = "";
        String balance = txtBalance.getText();
        String age = txtAge.getText();
        String aco = new BankManagementSystem().openAno();

        if (rbSaving.isSelected()) {
            accountType += "Saving";
        } else {
            accountType += "Current";
        }

        try {
            stmt = conn.createStatement();

            sql = "INSERT INTO `account_details`(`account_number`, `name`, `type`, `balance`, `occupation`, `age`, `password`) VALUES ('"
                    + aco + "', '" + name + "', '" + accountType + "', '" + balance + "', '" + occupation + "', '" + age
                    + "', '" + password + "')";

            if (stmt.executeUpdate(sql) == 1) {
                new Login(aco);
                dispose();
            }
        } catch (SQLException e) {
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
            
            new Signup();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
