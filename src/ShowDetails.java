import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import bank.BankManagementSystem;

public class ShowDetails extends JFrame implements TextListener {
    private Container c;
    private JButton btnGoBack, btnEdit, btnChangePass;
    private JLabel lblName, lblAcno, lblAccountType, lblBalance, lblOccupation, lblAge, lblPass;
    private JLabel lblNameErr, lblBalanceErr, lblOccupationErr, lblAgeErr, lblPassErr;
    private TextField txtName, txtAcno, txtAccountType, txtBalance, txtOccupation, txtAge, txtPass;

    private Connection conn;
    private Statement stmt;
    private String sql;
    private ResultSet rs;

    public ShowDetails() {
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
        setTitle("Show Details: BMS");

        try {
            stmt = conn.createStatement();

            sql = "SELECT * FROM `account_details` WHERE `logged_status` = '1'";
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e);
        }

        GridBagConstraints gbc = new GridBagConstraints();

        lblName = new JLabel("Name: ");
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblName, gbc);

        txtName = new TextField(20);
        txtName.setEditable(false);
        txtName.addTextListener(this);
        gbc.gridy = 0;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtName, gbc);

        lblNameErr = new JLabel(" ");
        lblNameErr.setForeground(Color.RED);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblNameErr, gbc);

        lblAcno = new JLabel("Account No: ");
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblAcno, gbc);

        txtAcno = new TextField(20);
        txtAcno.setEditable(false);
        txtAcno.setEnabled(false);
        gbc.gridy = 2;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtAcno, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(new JLabel(" "), gbc);

        lblAccountType = new JLabel("Account Type: ");
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblAccountType, gbc);

        txtAccountType = new TextField(20);
        txtAccountType.setEditable(false);
        txtAccountType.setEnabled(false);
        gbc.gridy = 4;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtAccountType, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(new JLabel(" "), gbc);

        lblBalance = new JLabel("Balance: ");
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblBalance, gbc);

        txtBalance = new TextField(20);
        txtBalance.setEditable(false);
        txtBalance.addTextListener(this);
        gbc.gridy = 6;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtBalance, gbc);

        lblBalanceErr = new JLabel(" ");
        lblBalanceErr.setForeground(Color.RED);
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblBalanceErr, gbc);

        lblOccupation = new JLabel("Occupation: ");
        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblOccupation, gbc);

        txtOccupation = new TextField(20);
        txtOccupation.setEditable(false);
        txtOccupation.addTextListener(this);
        gbc.gridy = 8;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtOccupation, gbc);

        lblOccupationErr = new JLabel(" ");
        lblOccupationErr.setForeground(Color.RED);
        gbc.gridy = 9;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblOccupationErr, gbc);

        lblAge = new JLabel("Age: ");
        gbc.gridy = 10;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblAge, gbc);

        txtAge = new TextField(20);
        txtAge.setEditable(false);
        txtAge.addTextListener(this);
        gbc.gridy = 10;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtAge, gbc);

        lblAgeErr = new JLabel(" ");
        lblAgeErr.setForeground(Color.RED);
        gbc.gridy = 11;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblAgeErr, gbc);

        lblPass = new JLabel("Password: ");
        gbc.gridy = 12;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblPass, gbc);

        txtPass = new TextField(20);
        txtPass.setEchoChar('*');
        txtPass.setEditable(false);
        txtPass.addTextListener(this);
        gbc.gridy = 12;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtPass, gbc);

        lblPassErr = new JLabel(" ");
        lblPassErr.setForeground(Color.RED);
        gbc.gridy = 13;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblPassErr, gbc);

        try {
            rs.next();
            txtName.setText(rs.getString("name"));
            txtAcno.setText(rs.getString("account_number"));
            txtAccountType.setText(rs.getString("type"));
            txtBalance.setText(rs.getString("balance"));
            txtOccupation.setText(rs.getString("occupation"));
            txtAge.setText(rs.getString("age"));
            txtPass.setText(rs.getString("password"));

            addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent we) {
                    btnEdit.requestFocus();
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }

        btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (btnEdit.getText().equals("Edit")) {
                    txtName.setEditable(true);
                    txtBalance.setEditable(true);
                    txtOccupation.setEditable(true);
                    txtAge.setEditable(true);

                    btnEdit.setText("Save");
                } else {
                    if (saveDetails()) {
                        txtName.setEditable(false);
                        txtBalance.setEditable(false);
                        txtOccupation.setEditable(false);
                        txtAge.setEditable(false);

                        btnEdit.setText("Edit");
                    }
                }
            }
        });
        gbc.gridy = 14;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
        gbc.insets = new Insets(15, 3, 10, 0);
        c.add(btnEdit, gbc);

        btnChangePass = new JButton("Change Password");
        btnChangePass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new ChangePass();
                dispose();
            }
        });
        gbc.gridy = 14;
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
        gbc.insets = new Insets(15, 3, 10, 0);
        c.add(btnChangePass, gbc);

        btnGoBack = new JButton("Go Back");
        btnGoBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Main();
                dispose();
            }
        });
        gbc.gridy = 14;
        gbc.gridx = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
        gbc.insets = new Insets(15, 3, 10, 0);
        c.add(btnGoBack, gbc);

        setVisible(true);
    }

    @Override
    public void textValueChanged(TextEvent e) {
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
        } else if (txtPass.getText().length() > 15) {
            lblPassErr.setText("Password length must be less than 15");
        } else if (txtPass.getText().equals(txtPass.getText().toLowerCase())) {
            lblPassErr.setText("Password must contain at least 1 Capital Letter");
        } else if (txtPass.getText().equals(txtPass.getText().toUpperCase())) {
            lblPassErr.setText("Password must contain at least 1 Capital Letter");
        } else if (!new BankManagementSystem().isDigitPresent(txtPass.getText())) {
            lblPassErr.setText("Password must contain at least 1 Digit");
        } else {
            lblPassErr.setText(" ");
        }
    }

    private boolean saveDetails() {
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
            return false;
        }

        try {
            sql = "UPDATE `account_details` SET `name` = '" + txtName.getText() + "', `balance` = '"
                    + txtBalance.getText() + "', `occupation` = '" + txtOccupation.getText() + "', `age` = '"
                    + txtAge.getText() + "', `password` = '" + txtPass.getText() + "' WHERE `account_number` = '"
                    + txtAcno.getText() + "'";
            if (stmt.executeUpdate(sql) == 1) {
                System.out.println("Updated");
                return true;
            } else {
                System.out.println("error");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            new ShowDetails();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
