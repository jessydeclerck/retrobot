package automation;


import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.W32APIOptions;

public class NativeWindowsEvents {

    public static final int WM_LBUTTONUP = 514;
    public static final int WM_LBUTTONDOWN = 513;
    final static String winTitle = "Dopeultiko-[HOG] - Dofus Retro v1.32.1";

    public static void clic(int x, int y) {
        final User32 user32 = Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        WinDef.HWND hwnd = user32.FindWindow(null, winTitle);
        //user32.SetForegroundWindow(hwnd);
        long param = x + (y << 16);
        WinDef.LPARAM lparam = new WinDef.LPARAM(param);
        WinDef.WPARAM wparam = new WinDef.WPARAM(0);

        //user32.FindWindowEx()

        user32.SendMessage(hwnd, WM_LBUTTONDOWN, wparam, lparam);
        user32.SendMessage(hwnd, WM_LBUTTONUP, wparam, lparam);
    }

}
