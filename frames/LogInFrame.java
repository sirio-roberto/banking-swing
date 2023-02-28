package banking.frames;

import banking.customComponents.CustomJButton;
import banking.entities.Card;
import banking.entities.dao.CardDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LogInFrame extends JDialog {

    public LogInFrame(JFrame mainFrame, CardDao cardDao) {
        setModal(true);
        setTitle("Banking Card App");
        setResizable(false);
        setSize(300, 350);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(loginLabel.getFont().deriveFont(Font.BOLD, 22));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel loginFieldsLabel = new JPanel(new GridLayout(8, 1));

        JLabel accountNumberLabel = new JLabel("Enter your card number:");
        JTextField accountNumberText = new JTextField();

        JLabel pinLabel = new JLabel("Enter your PIN:");
        JPasswordField pinText = new JPasswordField();

        JButton loginButton = new CustomJButton("LOGIN");

        loginFieldsLabel.add(new JLabel());
        loginFieldsLabel.add(accountNumberLabel);
        loginFieldsLabel.add(accountNumberText);
        loginFieldsLabel.add(pinLabel);
        loginFieldsLabel.add(pinText);

        loginPanel.add(loginLabel, BorderLayout.PAGE_START);
        loginPanel.add(loginFieldsLabel, BorderLayout.CENTER);
        loginPanel.add(loginButton, BorderLayout.SOUTH);

        add(loginPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setVisible(true);
                dispose();
            }
        });

        loginButton.addActionListener(e -> {
            Card card = cardDao.findByNumberAndPIN(accountNumberText.getText(), String.valueOf(pinText.getPassword()));
            if (card != null) {
                dispose();
                new LoggedFrame(mainFrame, card, cardDao);
            }
            else {
                JOptionPane.showConfirmDialog(loginPanel,
                        "Wrong card number or PIN", "Error",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
