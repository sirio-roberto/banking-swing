package banking.frames;

import banking.customComponents.CustomJButton;
import banking.entities.Card;
import banking.entities.dao.CardDao;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.*;
import java.text.NumberFormat;

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

        JPanel optionsPanel = new JPanel(new GridLayout(8, 1, 10, 10));

        JLabel optionsLabel = new JLabel("Select an option");
        optionsLabel.setFont(optionsLabel.getFont().deriveFont(17f));
        optionsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton getBalanceBtn = new CustomJButton("Show current balance");

        JButton addBalanceBtn = new CustomJButton("Add income");

        JButton doTransferBtn = new CustomJButton("Transfer money");

        JButton closeAccountBtn = new CustomJButton("Delete card");

        JButton logOutBtn = new CustomJButton("Log out");
        logOutBtn.setBackground(Color.BLACK);
        logOutBtn.setForeground(Color.WHITE);

        optionsPanel.add(new JLabel());
        optionsPanel.add(optionsLabel);
        optionsPanel.add(getBalanceBtn);
        optionsPanel.add(addBalanceBtn);
        optionsPanel.add(doTransferBtn);
        optionsPanel.add(closeAccountBtn);

        mainPanel.add(cardNumberLabel, BorderLayout.NORTH);
        mainPanel.add(optionsPanel, BorderLayout.CENTER);
        mainPanel.add(logOutBtn, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        getBalanceBtn.addActionListener(e -> {
            JPanel balancePanel = new JPanel(new GridLayout(1, 2, 10, 0));
            JLabel balanceLabel = new JLabel("Your current balance is: ");
            JLabel balanceValueLabel = new JLabel(String.valueOf(card.getBalance()));
            balanceValueLabel.setFont(balanceValueLabel.getFont().deriveFont(Font.BOLD));

            balancePanel.add(balanceLabel);
            balancePanel.add(balanceValueLabel);

            JOptionPane.showConfirmDialog(mainPanel,
                    balancePanel, "Balance details",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        });

        addBalanceBtn.addActionListener(e -> {
            JPanel balancePanel = new JPanel(new GridLayout(1, 2, 10, 0));
            JLabel balanceLabel = new JLabel("Your current balance is: ");

            JSpinner balanceSpinner = new JSpinner();
            balanceSpinner.setFont(balanceSpinner.getFont().deriveFont(Font.BOLD));
            balanceSpinner.setUI(new BasicSpinnerUI() {
                protected Component createNextButton() {
                    return null;
                }

                protected Component createPreviousButton() {
                    return null;
                }
            });

            balancePanel.add(balanceLabel);
            balancePanel.add(balanceSpinner);

            int selectedOption = JOptionPane.showConfirmDialog(mainPanel,
                    balancePanel, "Balance details",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (selectedOption == 0) {
                if ((Integer) balanceSpinner.getValue() > 0) {
                    card.addBalance((Integer) balanceSpinner.getValue());
                    cardDao.updateBalance(card);
                }
            }
        });

        logOutBtn.addActionListener(e2 -> {
            dispose();
        });

        closeAccountBtn.addActionListener(e2 -> {
            int selectedOption = JOptionPane.showConfirmDialog(mainPanel,
                    "Are you sure you'd like to delete this card?", "Confirmation message",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if (selectedOption == 0) {
                cardDao.delete(card);
                logOutBtn.doClick();
            }
        });

        setVisible(true);
    }
}
