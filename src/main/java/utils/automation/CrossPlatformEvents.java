package utils.automation;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


public class CrossPlatformEvents {

    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void clic(int x, int y) {
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void pressEKey() {
        robot.keyPress(KeyEvent.VK_E);
    }

    public static void releaseEKey() {
        robot.keyRelease(KeyEvent.VK_E);
    }
}
