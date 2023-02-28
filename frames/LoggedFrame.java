package banking.frames;

import banking.customComponents.CustomJButton;
import banking.entities.Card;
import banking.entities.dao.CardDao;

import javax.swing.*;
import java.awt.*;

public class LoggedFrame extends JDialog {

    public LoggedFrame(Card card, CardDao cardDao) {
        setModal(true);
        setTitle("Banking Card App");
        setResizable(false);
        setSize(300, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel cardNumberLabel = new JLabel(card.toString());
        cardNumberLabel.setFont(cardNumberLabel.getFont().deriveFont(Font.BOLD, 22));
        cardNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel optionsPanel = new JPanel(new GridLayout(7, 1, 10, 10));

        JLabel optionsLabel = new JLabel("Select an option");
        optionsLabel.setFont(optionsLabel.getFont().deriveFont(17f));
        optionsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton getBalanceBtn = new CustomJButton("Show current balance");

        JButton doTransferBtn = new CustomJButton("Transfer money");

        JButton closeAccountBtn = new CustomJButton("Delete card");

        JButton logOutBtn = new CustomJButton("Log out");
        logOutBtn.setBackground(Color.BLACK);
        logOutBtn.setForeground(Color.WHITE);

        optionsPanel.add(new JLabel());
        optionsPanel.add(optionsLabel);
        optionsPanel.add(getBalanceBtn);
        optionsPanel.add(doTransferBtn);
        optionsPanel.add(closeAccountBtn);

        mainPanel.add(cardNumberLabel, BorderLayout.NORTH);
        mainPanel.add(optionsPanel, BorderLayout.CENTER);
        mainPanel.add(logOutBtn, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
