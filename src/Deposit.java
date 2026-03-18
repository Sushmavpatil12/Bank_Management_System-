import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import bank.BankManagementSystem;

public class Deposit extends JFrame implements TextListener {
    private Container c;
    private JLabel lblAmount, lblTotal, lblStatus;
    private JLabel lblAmountErr;
    private TextField txtAmount, txtTotal;
    private JButton btnDeposit, btnGoBack;

    private Connection conn;
    private Statement stmt;
    private String sql;
    private ResultSet rs;

    public Deposit() {
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
        setTitle("Deposit: BMS");

        try {
            stmt = conn.createStatement();

            sql = "SELECT * FROM `account_details` WHERE `logged_status` = '1'";
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e);
        }

        GridBagConstraints gbc = new GridBagConstraints();

        lblAmount = new JLabel("Amount to Deposit: ");
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

        lblTotal = new JLabel("Total Amount: ");
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lblTotal, gbc);

        txtTotal = new TextField(20);
        txtTotal.setEditable(false);
        gbc.gridy = 3;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(txtTotal, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        c.add(new JLabel(" "), gbc);

        btnDeposit = new JButton("Deposit");
        btnDeposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                deposit();
            }
        });
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(30, 3, 3, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(btnDeposit, gbc);

        lblStatus = new JLabel(" ");
        gbc.gridy = 6;
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
        gbc.gridy = 7;
        gbc.gridx = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 3, 3, 0);
        gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
        c.add(btnGoBack, gbc);

        try {
            rs.next();
            txtTotal.setText(rs.getString("balance"));
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

    private void deposit() {
        long balance = Long.parseLong(txtTotal.getText());
        if (txtAmount.getText().isEmpty()) {
            lblAmountErr.setText("Please fill these Field");
            return;
        }

        balance += Long.parseLong(txtAmount.getText());

        try {
            sql = "UPDATE `account_details` SET `balance` = '" + balance + "' WHERE `account_number` = '"
                    + rs.getString("account_number") + "'";

            if (stmt.executeUpdate(sql) == 1) {
                System.out.println("Deposited");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        lblStatus.setText("Balance Updated ! Total: Rs." + balance);
        txtTotal.setText(String.valueOf(balance));
        txtAmount.setText("");
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
            new Deposit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
