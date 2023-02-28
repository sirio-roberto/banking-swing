package banking.customComponents;

import javax.swing.*;
import java.awt.*;

public class CustomJButton extends JButton {

    public CustomJButton(String text) {
        super(text);
        setBackground(Color.WHITE);
        setFocusPainted(false);
    }
}
