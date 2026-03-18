import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Main extends JFrame {
    private Container c;
    private JButton btnShowDetails, btnApplyLoan, btnPayLoan, btnDeposit, btnWithdraw, btnDelete, btnLogout;
    private JLabel lblTitle, lblMenu, lblLogin;
    private Panel main;

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private String sql = "";
    private String tempAcno;

    public Main() {
        connect();
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bank_management_system", "root", "");
            conn.createStatement();

            if (conn != null) {
                initFrame();
            }
        } catch (Exception e) {
            new Error404();
        }

    }

    private void initFrame() {
        boolean flag = true;
        c = getContentPane();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setTitle("Menu: BMS");

        lblLogin = new JLabel("", JLabel.CENTER);
        try {
            stmt = conn.createStatement();

            sql = "SELECT * FROM `account_details` WHERE `logged_status` = '1'";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                tempAcno = rs.getString("account_number");
                lblLogin.setText("You are Logged in as: " + rs.getString("name"));
            } else {
                new Login("0");
                flag = false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        if (flag) {
            GridBagConstraints gbc = new GridBagConstraints();
            main = new Panel(new GridBagLayout());

            lblTitle = new JLabel("Bank Management System");
            lblTitle.setFont(new Font("Cambria", Font.BOLD, 24));
            gbc.gridy = 0;
            gbc.gridx = 0;
            gbc.gridwidth = 6;
            gbc.insets = new Insets(10, 0, 30, 0);
            main.add(lblTitle, gbc);

            lblMenu = new JLabel("Menu: ");
            gbc.gridy = 1;
            gbc.gridx = 0;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(6, 6, 6, 6);
            main.add(lblMenu, gbc);

            btnShowDetails = new JButton("Show Account Details");
            btnShowDetails.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    new ShowDetails();
                    dispose();
                }
            });
            gbc.gridy = 2;
            gbc.gridx = 1;
            gbc.gridwidth = 4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 10, 5, 0);
            main.add(btnShowDetails, gbc);

            btnApplyLoan = new JButton("Apply for Loan");
            btnApplyLoan.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    new ApplyLoan();
                    dispose();
                }
            });
            gbc.gridy = 3;
            gbc.gridx = 1;
            gbc.gridwidth = 4;
            main.add(btnApplyLoan, gbc);

            btnPayLoan = new JButton("Pay Loan");
            btnPayLoan.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    new PayLoan();
                    dispose();
                }
            });
            gbc.gridy = 4;
            gbc.gridx = 1;
            gbc.gridwidth = 4;
            main.add(btnPayLoan, gbc);

            btnDeposit = new JButton("Deposit");
            btnDeposit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    new Deposit();
                    dispose();
                }
            });
            gbc.gridy = 5;
            gbc.gridx = 1;
            gbc.gridwidth = 4;
            main.add(btnDeposit, gbc);

            btnWithdraw = new JButton("Withdraw");
            btnWithdraw.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    new Withdraw();
                    dispose();
                }
            });
            gbc.gridy = 6;
            gbc.gridx = 1;
            gbc.gridwidth = 4;
            main.add(btnWithdraw, gbc);

            btnDelete = new JButton("Delete Your Account");
            btnDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    new DeleteAccount();
                    dispose();
                }
            });
            gbc.gridy = 7;
            gbc.gridx = 1;
            gbc.gridwidth = 4;
            main.add(btnDelete, gbc);

            btnLogout = new JButton("Log Out");
            btnLogout.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    logOut();
                }
            });
            gbc.gridy = 8;
            gbc.gridx = 1;
            gbc.gridwidth = 4;
            main.add(btnLogout, gbc);

            c.add(lblLogin, BorderLayout.NORTH);
            c.add(main);

            setVisible(true);
        }
    }

    private void logOut() {
        try {
            sql = "UPDATE `account_details` SET `logged_status` = '0' WHERE `account_number` = '" + tempAcno
                    + "'"; 
            stmt.executeUpdate(sql);

            new Login("0");
            dispose();
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
            
            new Main();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
