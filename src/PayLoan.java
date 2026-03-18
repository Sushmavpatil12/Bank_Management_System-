import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import bank.BankManagementSystem;

public class PayLoan extends JFrame implements TextListener {
    private Container c;
    private JLabel lblAmount, lblRemaining, lblTotal, lblBalance, lblStatus;
    private JLabel lblAmountErr;
    private TextField txtAmount, txtRemaining, txtTotal, txtBalance;
    private JButton btnReturn, btnGoBack;

    private Connection conn;
    private Statement stmt;
    private String sql;
    private ResultSet rs;

    public PayLoan() {
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
        setTitle("Pay Loan: BMS");

        try {
            stmt = conn.createStatement();

            sql = "SELECT * FROM `account_details` WHERE `logged_status` = '1'";
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e);
        }

        GridBagConstraints gbc = new GridBagConstraints();

        lblAmount = new JLabel("Amount to Return: ");
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

        lblRemaining = new JLabel("Remained Amount: ");
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblRemaining, gbc);

        txtRemaining = new TextField(20);
        txtRemaining.setEditable(false);
        gbc.gridy = 3;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtRemaining, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(new JLabel(" "), gbc);

        lblTotal = new JLabel("Total Amount: ");
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblTotal, gbc);

        txtTotal = new TextField(20);
        txtTotal.setEditable(false);
        gbc.gridy = 5;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtTotal, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(new JLabel(" "), gbc);

        lblBalance = new JLabel("Account Balance: ");
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblBalance, gbc);

        txtBalance = new TextField(20);
        txtBalance.setEditable(false);
        gbc.gridy = 7;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtBalance, gbc);

        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(new JLabel(" "), gbc);

        btnReturn = new JButton("Return");
        btnReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                pay();
            }
        });
        gbc.gridy = 9;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(30, 3, 3, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(btnReturn, gbc);

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
        gbc.gridx = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 3, 3, 0);
        gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
        c.add(btnGoBack, gbc);

        try {
            rs.next();
            if (!Boolean.parseBoolean(rs.getString("loan_applied"))) {
                lblStatus.setText(rs.getString("name") + " has not taken Loan");
                txtAmount.setEnabled(false);
                btnReturn.setEnabled(false);
            } else {
                txtAmount.setEnabled(true);
                btnReturn.setEnabled(true);

                txtTotal.setText(rs.getString("total_amount"));
                txtBalance.setText(rs.getString("balance"));
                txtRemaining.setText(rs.getString("remaining"));
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
    }

    private void pay() {
        if (txtAmount.getText().isEmpty()) {
            lblAmountErr.setText("Please fill these Field");
            return;
        }

        try {
            long remaining = Long.parseLong(rs.getString("remaining"));
            long paid = Long.parseLong(rs.getString("paid"));
            long total = Long.parseLong(rs.getString("total_amount"));
            long balance = Long.parseLong(rs.getString("balance"));

            if (Long.parseLong(txtAmount.getText()) > remaining) {
                lblAmountErr.setText("Cannot Enter Value Greater than Total");
                return;
            } else {
                if (Long.parseLong(txtAmount.getText()) > balance) {
                    lblAmountErr.setText("Not Sufficient Balance to Repay");
                    return;
                }

                paid += Long.parseLong(txtAmount.getText());
                remaining = total - paid;
                lblStatus.setForeground(Color.BLACK);
                lblStatus.setText("Total " + paid + " Rupees Returned");
                balance -= Long.parseLong(txtAmount.getText());

                if (remaining == 0) {
                    lblStatus.setText("You've cleared your loan !");
                    try {
                        sql = "UPDATE `account_details` SET `balance` = '" + balance
                                + "', `loan_applied` = 'false', `loan_amount` = '0', `total_amount` = '0', `duration` = '0', `paid` = '0', `remaining` = '0' WHERE `account_number` = '"
                                + rs.getString("account_number") + "'";
                        if (stmt.executeUpdate(sql) == 1) {
                            System.out.println("100% returned");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    txtAmount.setEnabled(false);
                    txtAmount.setText("");
                    txtRemaining.setText(String.valueOf(remaining));
                    txtTotal.setText(String.valueOf(total));
                    txtBalance.setText(String.valueOf(balance));

                    btnReturn.setEnabled(false);
                    return;
                }

                txtRemaining.setText(String.valueOf(remaining));
                txtAmount.setText("");
                txtTotal.setText(rs.getString("total_amount"));
                txtBalance.setText(String.valueOf(balance));

                try {
                    sql = "UPDATE `account_details` SET `balance` = '" + balance + "', `paid` = '" + paid
                            + "', `remaining` = '" + remaining + "' WHERE `account_number` = '"
                            + rs.getString("account_number") + "'";
                    if (stmt.executeUpdate(sql) == 1) {
                        System.out.println("returned");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
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
            
            new PayLoan();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
