package net.thal0rin.titlebarchanger.dllapi;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface DwmApi extends StdCallLibrary {
    DwmApi INSTANCE = Native.load("dwmapi", DwmApi.class, W32APIOptions.DEFAULT_OPTIONS);

    WinNT.HRESULT DwmSetWindowAttribute(WinDef.HWND var1, WinDef.DWORD var2, WinDef.LPVOID var3, WinDef.DWORD var4);
}
