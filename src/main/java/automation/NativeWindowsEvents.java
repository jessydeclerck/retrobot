package automation;


import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.W32APIOptions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NativeWindowsEvents {

    public static final int WM_LBUTTONUP = 514;
    public static final int WM_LBUTTONDOWN = 513;
    final static User32 user32 = Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
    public static WinDef.HWND hwnd;

    public static void clic(int x, int y) {

        long param = x + (y << 16);
        WinDef.LPARAM lparam = new WinDef.LPARAM(param);
        WinDef.WPARAM wparam = new WinDef.WPARAM(0);


        user32.SendMessage(hwnd, WM_LBUTTONDOWN, wparam, lparam);
        user32.SendMessage(hwnd, WM_LBUTTONUP, wparam, lparam);
    }

    public static void clic(double x, double y) {
        int roundedX = (int) Math.round(x);
        int roundedY = (int) Math.round(y);
        log.debug("Clic {}, {}", roundedX, roundedY);
        long param = roundedX + (roundedY << 16);
        WinDef.LPARAM lparam = new WinDef.LPARAM(param);
        WinDef.WPARAM wparam = new WinDef.WPARAM(0);


        user32.SendMessage(hwnd, WM_LBUTTONDOWN, wparam, lparam);
        user32.SendMessage(hwnd, WM_LBUTTONUP, wparam, lparam);
    }

    public static void prepareForAutomation(String winTitle) {
        hwnd = user32.FindWindow(null, winTitle);
        /**
         * i = x
         * i1 = y
         * i2 = largeur
         * i3 = hauteur
         * i4 = yolo
         */
        user32.SetWindowPos(hwnd, hwnd, 300, 300, 961, 768, 300);
    }

}
