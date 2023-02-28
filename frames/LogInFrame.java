package banking.frames;

import banking.customComponents.CustomJButton;
import banking.entities.Card;
import banking.entities.dao.CardDao;

import javax.swing.*;
import java.awt.*;

public class LogInFrame extends JDialog {

    public LogInFrame(CardDao cardDao) {
        setModal(true);
        setTitle("Banking Card App");
        setResizable(false);
        setSize(300, 350);
        setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(loginLabel.getFont().deriveFont(Font.BOLD, 22));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel loginFieldsLabel = new JPanel(new GridLayout(8, 1));

        JLabel accountNumberLabel = new JLabel("Enter your card number:");
        JTextField accountNumberText = new JTextField();

        JLabel pinLabel = new JLabel("Enter your PIN:");
        pinLabel.setBounds(0, 100, 200, 30);
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

        loginButton.addActionListener(e -> {
            Card card = cardDao.findByNumberAndPIN(accountNumberText.getText(), String.valueOf(pinText.getPassword()));
            dispose();
            if (card != null) {
                new LoggedFrame(card, cardDao);
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
