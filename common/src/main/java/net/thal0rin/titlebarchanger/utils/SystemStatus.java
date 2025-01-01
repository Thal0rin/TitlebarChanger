package net.thal0rin.titlebarchanger.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;
import net.thal0rin.titlebarchanger.TitlebarChanger;

import java.util.Arrays;
import java.util.List;

public enum SystemStatus {
    SUITABLE, LIMITED_SUITABILITY, NOT_SUITABLE;

    /**
     * Проверяет версию операционной системы и возвращает статус совместимости.
     *
     * @return Статус совместимости.
     */
    public static SystemStatus checkSystem() {
        try {
            OSVERSIONINFOEX.ByReference osVersionInfo = new OSVERSIONINFOEX.ByReference();
            osVersionInfo.dwOSVersionInfoSize.setValue(osVersionInfo.size());

            int result = Ntdll.INSTANCE.RtlGetVersion(osVersionInfo);
            if (result != 0) {
                TitlebarChanger.LOGGER.error("Failed to retrieve OS version info, error code: {}", result);
                return NOT_SUITABLE;
            }

            int major = osVersionInfo.dwMajorVersion.intValue();
            int build = osVersionInfo.dwBuildNumber.intValue();

            if (major == 10 && build >= 22000) {
                TitlebarChanger.LOGGER.info("Detected Windows 11, build number {}.{}", major, build);
                return SUITABLE;
            } else if (major == 10) {
                TitlebarChanger.LOGGER.warn("Detected Windows 10, build number {}.{}. Some features may be limited.", major, build);
                return LIMITED_SUITABILITY;
            } else {
                TitlebarChanger.LOGGER.error("Unsupported OS version: {}.{}. This mod requires Windows 10 or later.", major, build);
                return NOT_SUITABLE;
            }
        } catch (Exception e) {
            TitlebarChanger.LOGGER.error("An error occurred while checking the system version: {}", e.getMessage());
            return NOT_SUITABLE;
        }
    }

    /**
     * Структура для хранения информации о версии ОС.
     */
    public static class OSVERSIONINFOEX extends Structure {
        public WinDef.DWORD dwOSVersionInfoSize = new WinDef.DWORD();
        public WinDef.DWORD dwMajorVersion = new WinDef.DWORD();
        public WinDef.DWORD dwMinorVersion = new WinDef.DWORD();
        public WinDef.DWORD dwBuildNumber = new WinDef.DWORD();
        public WinDef.DWORD dwPlatformId = new WinDef.DWORD();
        public byte[] szCSDVersion = new byte[128];
        public WinDef.WORD wServicePackMajor = new WinDef.WORD();
        public WinDef.WORD wServicePackMinor = new WinDef.WORD();
        public WinDef.WORD wSuiteMask = new WinDef.WORD();
        public byte wProductType;
        public byte wReserved;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList(
                    "dwOSVersionInfoSize", "dwMajorVersion", "dwMinorVersion", "dwBuildNumber", "dwPlatformId",
                    "szCSDVersion", "wServicePackMajor", "wServicePackMinor", "wSuiteMask", "wProductType", "wReserved"
            );
        }

        public static class ByReference extends OSVERSIONINFOEX implements Structure.ByReference {
        }
    }

    /**
     * Интерфейс для работы с ntdll.dll.
     */
    public interface Ntdll extends Library {
        Ntdll INSTANCE = Native.load("ntdll", Ntdll.class);

        int RtlGetVersion(OSVERSIONINFOEX.ByReference osVersionInfo);
    }
}