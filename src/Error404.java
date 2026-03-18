import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Error404 extends JFrame {
    private JLabel lblError, lbl404;
    private JButton btnExit, btnRetry;
    private Container c;

    private Connection conn;

    public Error404() {
        connect();
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bank_management_system", "root", "");
            conn.createStatement();

            if (conn != null) {
                new Login("0");
                dispose();
            }
        } catch (Exception e) {
            initFrame();
        }
    }

    private void initFrame() {
        c = getContentPane();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(400, 500);
        setLocationRelativeTo(null);
        setTitle("Error 404");

        GridBagConstraints gbc = new GridBagConstraints();

        lblError = new JLabel(new ImageIcon("image/exclamation_triangle.png"));
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 4;
        c.add(lblError, gbc);

        lbl404 = new JLabel("404 Error", JLabel.CENTER);
        lbl404.setFont(new Font("Cambria", Font.BOLD, 25));
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.gridwidth = 4;
        c.add(lbl404, gbc);

        btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 0, 20);
        gbc.ipadx = 20;
        c.add(btnExit, gbc);

        btnRetry = new JButton("Retry");
        btnRetry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/bank_management_system", "root", "");
                    conn.createStatement();

                    if (conn != null) {
                        new Login("0");
                        dispose();
                    }
                } catch (Exception e) {
                }
            }
        });
        gbc.gridy = 3;
        gbc.gridx = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 0, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 20;
        c.add(btnRetry, gbc);

        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            new Error404();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}