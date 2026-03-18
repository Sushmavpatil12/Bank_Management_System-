import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import bank.BankManagementSystem;

public class ApplyLoan extends JFrame implements TextListener {
    private Container c;
    private JLabel lblAmount, lblDuration, lblROT, lblTotal, lblStatus;
    private JLabel lblAmountErr, lblDurationErr;
    private TextField txtAmount, txtDuration, txtROT, txtTotal;
    private JButton btnApply, btnGoBack;

    private Connection conn;
    private Statement stmt;
    private String sql;
    private ResultSet rs;

    public ApplyLoan() {
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
        setTitle("Apply Loan: BMS");

        try {
            stmt = conn.createStatement();

            sql = "SELECT * FROM `account_details` WHERE `logged_status` = '1'";
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        GridBagConstraints gbc = new GridBagConstraints();

        lblAmount = new JLabel("Amount: ");
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblAmount, gbc);

        txtAmount = new TextField(20);
        txtAmount.addTextListener(this);
        gbc.gridy = 1;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtAmount, gbc);

        lblAmountErr = new JLabel(" ");
        lblAmountErr.setForeground(Color.RED);
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblAmountErr, gbc);

        lblDuration = new JLabel("Duration: ");
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblDuration, gbc);

        txtDuration = new TextField(20);
        txtDuration.addTextListener(this);
        gbc.gridy = 3;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtDuration, gbc);

        lblDurationErr = new JLabel(" ");
        lblDurationErr.setForeground(Color.RED);
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(lblDurationErr, gbc);

        lblROT = new JLabel("Rate of Interest: ");
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblROT, gbc);

        txtROT = new TextField("0.7 (FIXED)", 20);
        txtROT.setEnabled(false);
        txtROT.setEditable(false);
        gbc.gridy = 5;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtROT, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(new JLabel(" "), gbc);

        lblTotal = new JLabel("Total Amount: ");
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblTotal, gbc);

        txtTotal = new TextField(20);
        txtTotal.setEnabled(false);
        txtTotal.setEditable(false);
        txtTotal.addTextListener(this);
        gbc.gridy = 7;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtTotal, gbc);

        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(new JLabel(" "), gbc);

        btnApply = new JButton("Apply for Loan");
        btnApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                apply();
            }
        });
        gbc.gridy = 9;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(30, 3, 3, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(btnApply, gbc);

        lblStatus = new JLabel(" ");
        lblStatus.setForeground(Color.RED);
        gbc.gridy = 10;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(30, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblStatus, gbc);

        btnGoBack = new JButton("Go Back");
        btnGoBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Main();
                dispose();
            }
        });
        gbc.gridy = 11;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 3, 3, 0);
        gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
        c.add(btnGoBack, gbc);

        try {
            rs.next();
            if (Integer.parseInt(rs.getString("age")) < 18) {
                lblStatus.setText("You are not Eligible to Borrow Loan");
                txtAmount.setEnabled(false);
                txtDuration.setEnabled(false);
                btnApply.setEnabled(false);
            } else if (rs.getString("loan_applied").equals("true")) {
                lblStatus.setText(rs.getString("name") + " has already taken Loan");
                txtAmount.setEnabled(false);
                txtDuration.setEnabled(false);
                btnApply.setEnabled(false);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        setVisible(true);
    }

    @Override
    public void textValueChanged(TextEvent te) {
        if (txtAmount.getText().isEmpty()) {
            lblAmountErr.setText(" ");
        } else if (new BankManagementSystem().checkLong(txtAmount.getText()) == -1) {
            lblAmountErr.setText("Loan Amount must contain only digits");
        } else {
            lblAmountErr.setText(" ");
        }

        if (txtDuration.getText().isEmpty()) {
            lblDurationErr.setText(" ");
        } else if (new BankManagementSystem().checkLong(txtDuration.getText()) == -1) {
            lblDurationErr.setText("Loan Duration must contain only digits");
        } else if (new BankManagementSystem().checkLong(txtDuration.getText()) > 10) {
            lblDurationErr.setText("Loan Duration must be less than 5");
        } else {
            lblDurationErr.setText(" ");
        }

        try {
            long years = Long.parseLong(txtDuration.getText());
            long amount = new BankManagementSystem().checkLong(txtAmount.getText());
            float temp = amount * (1 + (0.7f * years));
            long total = Math.round(temp);

            txtTotal.setText(String.valueOf(total));
        } catch (Exception e) {
            txtTotal.setText("0");
        }
    }

    private void apply() {
        boolean returnFlag = false;

        if (txtAmount.getText().equals(" ")) {
            lblAmountErr.setText("Please fill these Field");
            returnFlag = true;
        }

        if (txtDuration.getText().equals(" ")) {
            lblDurationErr.setText("Please fill these Field");
            returnFlag = true;
        }

        if (returnFlag) {
            return;
        }

        try {
            sql = "UPDATE `account_details` SET `loan_applied` = 'true', `loan_amount` = '" + txtAmount.getText()
                    + "', `total_amount` = '" + txtTotal.getText() + "', `duration` = '" + txtDuration.getText()
                    + "', `paid` = '0', `remaining` = '" + txtTotal.getText() + "' WHERE `account_number` = '"
                    + rs.getString("account_number") + "'";
            if (stmt.executeUpdate(sql) == 1) {
                System.out.println("Applied");

                lblStatus.setForeground(Color.BLACK);
                lblStatus.setText("Amount Rs. " + txtTotal.getText() + " Loan Applied");
                txtAmount.setText("");
                txtDuration.setText("");

                txtAmount.setEnabled(false);
                txtDuration.setEnabled(false);
                btnApply.setEnabled(false);
            } else {
                System.out.println("error");
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
            
            new ApplyLoan();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
