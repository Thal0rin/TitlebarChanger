package net.thal0rin.titlebarchanger;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.thal0rin.titlebarchanger.config.ConfigFile;
import net.thal0rin.titlebarchanger.dllapi.DwmApi;
import net.thal0rin.titlebarchanger.utils.ColorConverter;
import net.thal0rin.titlebarchanger.utils.SystemStatus;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TitlebarChanger {
    public static final String MOD_ID = "titlebarchanger";
    public static final Logger LOGGER = LoggerFactory.getLogger(TitlebarChanger.MOD_ID);
    public static ConfigFile configFile = new ConfigFile();
    public static SystemStatus systemStatus;
    public static Mode mode;
    public static Memory memory;

    public static void init() {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            systemStatus = SystemStatus.checkSystem();
            configFile.loadConfig();
            Minecraft.getInstance().execute(() -> {
                Mode[] mode = Mode.values();
                for (Mode modeOption : mode) {
                    TitlebarChanger.mode = modeOption;
                    applyChanges();
                }
            });
        } else {
            systemStatus = SystemStatus.NOT_SUITABLE;
            LOGGER.error("TitlebarChanger only supports Windows");
        }
    }

    public static void applyChanges() {
        WinDef.DWORD dwmMode = null;
        long glfwWindow = Minecraft.getInstance().getWindow().getWindow();
        long hwndWindow = GLFWNativeWin32.glfwGetWin32Window(glfwWindow);
        WinDef.HWND hwnd = new WinDef.HWND(Pointer.createConstant(hwndWindow));
        if (systemStatus == SystemStatus.SUITABLE) {
            switch (mode) {
                case DARK_THEME_MODE:
                    if (configFile.getConfig().getTheme() < 2) {
                        cleanUp(hwnd);
                        memory.setInt(0L, configFile.getConfig().getTheme());
                        dwmMode = new WinDef.DWORD(20L);
                    }
                    break;
                case CORNER_MODE:
                    memory.setInt(0L, configFile.getConfig().getCorner());
                    dwmMode = new WinDef.DWORD(33L);
                    break;
                case TITLE_BAR_COLOR_MODE:
                    if (configFile.getConfig().getTheme() == 2) {
                        memory.setInt(0L, ColorConverter.rgbToHex(configFile.getConfig().getTitleBarColor().getR(), configFile.getConfig().getTitleBarColor().getG(), configFile.getConfig().getTitleBarColor().getB()));
                        dwmMode = new WinDef.DWORD(35L);
                    }
                    break;
                case TITLE_BAR_TEXT_COLOR_MODE:
                    if (configFile.getConfig().getTheme() == 2) {
                        memory.setInt(0L, ColorConverter.rgbToHex(configFile.getConfig().getTitleBarTextColor().getR(), configFile.getConfig().getTitleBarTextColor().getG(), configFile.getConfig().getTitleBarTextColor().getB()));
                        dwmMode = new WinDef.DWORD(36L);
                    }
                    break;
                case TITLE_BAR_STROKE_COLOR_MODE:
                    if (configFile.getConfig().getTheme() == 2) {
                        memory.setInt(0L, ColorConverter.rgbToHex(configFile.getConfig().getTitleBarStrokeColor().getR(), configFile.getConfig().getTitleBarStrokeColor().getG(), configFile.getConfig().getTitleBarStrokeColor().getB()));
                        dwmMode = new WinDef.DWORD(34L);
                    }
                    break;
                default:
                    LOGGER.warn("Unexpected mode: {}", mode);
            }

            DwmApi.INSTANCE.DwmSetWindowAttribute(hwnd, dwmMode, new WinDef.LPVOID(memory), new WinDef.DWORD(4L));
        } else if (systemStatus == SystemStatus.LIMITED_SUITABILITY) {
            memory.setInt(0L, configFile.getConfig().getTheme());
            dwmMode = new WinDef.DWORD(20L);
            int width = Minecraft.getInstance().getWindow().getWidth();
            int height = Minecraft.getInstance().getWindow().getHeight();
            GLFW.glfwSetWindowSize(Minecraft.getInstance().getWindow().getWindow(), 0, 0);
            DwmApi.INSTANCE.DwmSetWindowAttribute(hwnd, dwmMode, new WinDef.LPVOID(memory), new WinDef.DWORD(4L));

            try {
                Thread.sleep(50L);
            } catch (InterruptedException var9) {
                InterruptedException e = var9;
                throw new RuntimeException(e);
            }

            GLFW.glfwSetWindowSize(Minecraft.getInstance().getWindow().getWindow(), width, height);
        }

    }

    public static void cleanUp(WinDef.HWND hwnd) {
        List<Integer> codes = new ArrayList();
        codes.add(35);
        codes.add(36);
        codes.add(34);

        for (int code : codes) {
            WinDef.DWORD dwmMode = new WinDef.DWORD(code);
            memory.setInt(0L, -1);
            DwmApi.INSTANCE.DwmSetWindowAttribute(hwnd, dwmMode, new WinDef.LPVOID(memory), new WinDef.DWORD(4L));
        }
    }

    static {
        memory = new Memory(Native.POINTER_SIZE);
    }
}
