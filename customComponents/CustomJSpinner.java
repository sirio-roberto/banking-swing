package banking.customComponents;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.*;

public class CustomJSpinner extends JSpinner {

    public CustomJSpinner() {
        setFont(getFont().deriveFont(Font.BOLD));

        setUI(new BasicSpinnerUI() {
            protected Component createNextButton() {
                return null;
            }

            protected Component createPreviousButton() {
                return null;
            }
        });
    }
}
