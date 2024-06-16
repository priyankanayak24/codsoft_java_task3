import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ATMInterface {

    private static final String PIN = "1234";
    private static double balance = 1000.0;
    private static ArrayList<String> transactionHistory = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("ATM Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        JPanel cardPanel = new JPanel(new CardLayout());

        // First Page: Welcome and PIN Entry
        JPanel welcomePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(0, 0, 255);
                Color color2 = new Color(238, 130, 238);
                Color color3 = new Color(255, 192, 203);
                GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        welcomePanel.setLayout(new GridBagLayout());

        JLabel welcomeLabel = new JLabel("Welcome to the State Bank of India");
        welcomeLabel.setForeground(Color.WHITE);
        JLabel pinLabel = new JLabel("Enter PIN:");
        pinLabel.setForeground(Color.WHITE);
        JPasswordField pinField = new JPasswordField(10);
        JButton pinButton = new JButton("Submit");

        pinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredPin = new String(pinField.getPassword());
                if (PIN.equals(enteredPin)) {
                    CardLayout cl = (CardLayout) (cardPanel.getLayout());
                    cl.show(cardPanel, "optionsPanel");
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect PIN. Try again.");
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        welcomePanel.add(welcomeLabel, gbc);
        gbc.gridy = 1;
        welcomePanel.add(pinLabel, gbc);
        gbc.gridy = 2;
        welcomePanel.add(pinField, gbc);
        gbc.gridy = 3;
        welcomePanel.add(pinButton, gbc);

        // Second Page: Options
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        optionsPanel.setBackground(Color.CYAN);

        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton balanceButton = new JButton("Check Balance");
        JButton historyButton = new JButton("Transaction History");
        JButton exitButton = new JButton("Exit");

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;

        withdrawButton.setPreferredSize(new Dimension(150, 50));
        depositButton.setPreferredSize(new Dimension(150, 50));
        balanceButton.setPreferredSize(new Dimension(150, 50));
        historyButton.setPreferredSize(new Dimension(150, 50));
        exitButton.setPreferredSize(new Dimension(150, 50));

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog(frame, "Enter amount to withdraw:");
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount > 0 && amount <= balance) {
                        balance -= amount;
                        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        transactionHistory.add("Withdrew: $" + amount + " on " + timestamp);
                        JOptionPane.showMessageDialog(frame, "Successfully withdrew $" + amount);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Insufficient balance or invalid amount.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid amount.");
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog(frame, "Enter amount to deposit:");
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount > 0) {
                        balance += amount;
                        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        transactionHistory.add("Deposited: $" + amount + " on " + timestamp);
                        JOptionPane.showMessageDialog(frame, "Successfully deposited $" + amount);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid amount.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid amount.");
                }
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Current balance: $" + balance);
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder history = new StringBuilder();
                for (String transaction : transactionHistory) {
                    history.append(transaction).append("\n");
                }
                JOptionPane.showMessageDialog(frame, history.length() > 0 ? history.toString() : "No transactions yet.");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        optionsPanel.add(withdrawButton, c);
        optionsPanel.add(depositButton, c);
        optionsPanel.add(balanceButton, c);
        optionsPanel.add(historyButton, c);
        optionsPanel.add(exitButton, c);

        cardPanel.add(welcomePanel, "welcomePanel");
        cardPanel.add(optionsPanel, "optionsPanel");

        frame.add(cardPanel);
        frame.setVisible(true);
    }
}
