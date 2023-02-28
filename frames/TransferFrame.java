package banking.frames;

import banking.customComponents.CustomJButton;
import banking.customComponents.CustomJSpinner;
import banking.entities.Card;
import banking.entities.dao.CardDao;

import javax.swing.*;
import java.awt.*;

public class TransferFrame extends JDialog {

    public TransferFrame(CardDao cardDao, Card card) {
        setModal(true);
        setTitle("Banking Card App");
        setResizable(false);
        setSize(300, 350);
        setLayout(new BorderLayout());

        JPanel transferPanel = new JPanel(new BorderLayout());
        transferPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel transferLabel = new JLabel("Transfer");
        transferLabel.setFont(transferLabel.getFont().deriveFont(Font.BOLD, 22));
        transferLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel transferFieldsLabel = new JPanel(new GridLayout(8, 1));

        JLabel destinationNumberLabel = new JLabel("Enter the destination card number:");
        JTextField destinationNumberText = new JTextField();

        JLabel amountLabel = new JLabel("Enter the transfer amount:");
        amountLabel.setBounds(0, 100, 200, 30);
        JSpinner amountText = new CustomJSpinner();

        JButton loginButton = new CustomJButton("LOGIN");

        transferFieldsLabel.add(new JLabel());
        transferFieldsLabel.add(destinationNumberLabel);
        transferFieldsLabel.add(destinationNumberText);
        transferFieldsLabel.add(amountLabel);
        transferFieldsLabel.add(amountText);

        transferPanel.add(transferLabel, BorderLayout.PAGE_START);
        transferPanel.add(transferFieldsLabel, BorderLayout.CENTER);
        transferPanel.add(loginButton, BorderLayout.SOUTH);

        add(transferPanel);
    }
}
