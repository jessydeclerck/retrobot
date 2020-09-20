package com.retrobot.utils;

import java.awt.*;

public class NotificationUtils {

    public static void displayMessage(String message) {
        SystemTray tray = SystemTray.getSystemTray();
        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage(new byte[0]));
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        trayIcon.displayMessage("Retrobot", message, TrayIcon.MessageType.INFO);
    }

}
