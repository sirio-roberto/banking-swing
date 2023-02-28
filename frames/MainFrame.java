package banking.frames;

import banking.customComponents.CustomJButton;
import banking.entities.Card;
import banking.entities.dao.CardDao;
import banking.entities.dao.DaoFactory;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    CardDao cardDao = DaoFactory.createCardDao("C:/sqlite/bankCard.db");

    public MainFrame() {
        setTitle("Banking Card App");
        setResizable(false);
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to your preferred Banking App");
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(18f));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton createAccountButton = new CustomJButton("Create Account");

        JButton logInButton = new CustomJButton("Log in");

        buttonsPanel.add(createAccountButton);
        buttonsPanel.add(logInButton);
        add(buttonsPanel, BorderLayout.PAGE_END);

        createAccountButton.addActionListener(e -> {
            Card card = new Card();
            cardDao.insert(card);

            Color textBackColor;
            JPanel accountDetailsPanel = new JPanel(new GridLayout(2, 2));
            JLabel accountNumberLabel = new JLabel("Your card number: ");
            textBackColor = accountNumberLabel.getBackground();
            JTextArea accountNumberText = new JTextArea(card.toString());
            accountNumberText.setBackground(textBackColor);
            JLabel pinLabel = new JLabel("Your card PIN: ");
            accountNumberText.setEditable(false);
            JTextArea pinText = new JTextArea(card.getPIN());
            pinText.setBackground(textBackColor);
            pinText.setEditable(false);
            accountDetailsPanel.add(accountNumberLabel);
            accountDetailsPanel.add(accountNumberText);
            accountDetailsPanel.add(pinLabel);
            accountDetailsPanel.add(pinText);
            JOptionPane.showConfirmDialog(buttonsPanel,
                    accountDetailsPanel, "Your card has been created!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        });

        logInButton.addActionListener(e -> {
            new LogInFrame(cardDao);
        });

        setVisible(true);
    }
}
