import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class DeleteAccount extends JFrame implements ActionListener {
    private Container c;
    private JLabel lblConfirm, lblStatus;
    private JButton btnYes, btnNo, btnGoBack;
    private Panel p, footer;

    private Connection conn;
    private Statement stmt;
    private String sql;
    private ResultSet rs;

    public DeleteAccount() {
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
        setTitle("Delete Account: BMS");
        
        try {
            stmt = conn.createStatement();

            sql = "SELECT * FROM `account_details` WHERE `logged_status` = '1'";
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e);
        }

        GridBagConstraints gbc = new GridBagConstraints();

        p = new Panel(new BorderLayout());
        footer = new Panel(new FlowLayout(FlowLayout.CENTER, 10, 30));

        lblConfirm = new JLabel("Are you sure want to delete your account ?", JLabel.CENTER);
        lblConfirm.setFont(new java.awt.Font("Thoma", Font.BOLD, 14));
        p.add(lblConfirm);

        btnYes = new JButton("YES");
        footer.add(btnYes);
        btnYes.addActionListener(this);

        btnNo = new JButton("NO");
        footer.add(btnNo);
        btnNo.addActionListener(this);

        p.add(footer, BorderLayout.SOUTH);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        c.add(p, gbc);

        lblStatus = new JLabel(" ");
        gbc.gridy = 2;
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
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.ipadx = 0;
        gbc.insets = new Insets(3, 3, 3, 0);
        gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
        c.add(btnGoBack, gbc);

        try {
            rs.next();
            if (Boolean.parseBoolean(rs.getString("loan_applied"))) {
                lblStatus.setText("Can't Delete Account, Loan Taken");

                btnYes.setEnabled(false);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("YES")) {
            try {
                lblStatus.setText(rs.getString("name") + "'s Account Deleted");
                sql = "DELETE FROM `account_details` WHERE `account_number` = '" + rs.getString("account_number") + "'";

                if (stmt.executeUpdate(sql) == 1) {
                    System.out.println("deleted");

                    new Login("0");
                    dispose();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else{
            new Main();
            dispose();
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
            
            new DeleteAccount();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
