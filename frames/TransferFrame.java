package banking.frames;

import banking.customComponents.CustomJButton;
import banking.customComponents.CustomJSpinner;
import banking.entities.Card;
import banking.entities.dao.CardDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TransferFrame extends JDialog {

    private Card card2;

    public TransferFrame(LoggedFrame mainFrame, CardDao cardDao, Card card) {
        setModal(true);
        setTitle("Banking Card App");
        setResizable(false);
        setSize(300, 350);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JPanel transferPanel = new JPanel(new BorderLayout());
        transferPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel transferLabel = new JLabel("Transfer");
        transferLabel.setFont(transferLabel.getFont().deriveFont(Font.BOLD, 22));
        transferLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel transferFieldsLabel = new JPanel(new GridLayout(8, 1));

        JLabel destinationNumberLabel = new JLabel("Enter the destination card number:");
        JTextField destinationNumberText = new JTextField();

        JLabel amountLabel = new JLabel("Enter the transfer amount:");
        amountLabel.setVisible(false);

        JSpinner amountText = new CustomJSpinner();
        amountText.setVisible(false);

        JButton validateNumberButton = new CustomJButton("Validate");

        JButton transferButton = new CustomJButton("TRANSFER");
        transferButton.setBackground(Color.BLACK);
        transferButton.setForeground(Color.WHITE);
        transferButton.setEnabled(false);

        transferFieldsLabel.add(new JLabel());
        transferFieldsLabel.add(destinationNumberLabel);
        transferFieldsLabel.add(destinationNumberText);
        transferFieldsLabel.add(amountLabel);
        transferFieldsLabel.add(amountText);
        transferFieldsLabel.add(new JLabel());
        transferFieldsLabel.add(validateNumberButton);

        transferPanel.add(transferLabel, BorderLayout.PAGE_START);
        transferPanel.add(transferFieldsLabel, BorderLayout.CENTER);
        transferPanel.add(transferButton, BorderLayout.PAGE_END);

        add(transferPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                mainFrame.setVisible(true);
            }
        });

        validateNumberButton.addActionListener(l -> {
            if (Card.isCardNumberValid(destinationNumberText.getText())) {
                card2 = cardDao.findByNumber(destinationNumberText.getText());
                if (card2 != null) {
                    if (!card.equals(card2)) {
                        destinationNumberText.setEnabled(false);
                        validateNumberButton.setEnabled(false);
                        amountLabel.setVisible(true);
                        amountText.setVisible(true);
                        transferButton.setEnabled(true);

                    } else {
                        JOptionPane.showConfirmDialog(this,
                                "You can't transfer money to the same account!", "Error",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showConfirmDialog(this,
                            "Such a card does not exist.", "Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showConfirmDialog(this,
                        "Probably you made a mistake in the card number. Please try again!", "Error",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        });

        transferButton.addActionListener(e -> {
            int moneyToTransfer = (Integer) amountText.getValue();
            if (moneyToTransfer > 0) {
                if (card.getBalance() >= moneyToTransfer) {
                    int card1Balance = card.getBalance();
                    int card2Balance = card2.getBalance();

                    int selectedOption = JOptionPane.showConfirmDialog(this,
                            "Are you sure you'd like to transfer this amount?", "Confirmation message",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (selectedOption == 0) {
                        card.setBalance(card1Balance - moneyToTransfer);
                        card2.setBalance(card2Balance + moneyToTransfer);
                        if (cardDao.transferMoney(card, card2)) {
                            JOptionPane.showConfirmDialog(this,
                                    "Money transferred successfully", "Success",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            card.setBalance(card1Balance);
                            card2.setBalance(card2Balance);
                        }
                    }
                }
            }
        });

        setVisible(true);
    }
}
