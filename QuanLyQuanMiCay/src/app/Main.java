package app;

import javax.swing.SwingUtilities;
import util.UiTheme;
import view.frame.LoginFrame;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UiTheme.applyGlobal();
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
