package net.thal0rin.titlebarchanger.ui.select_color;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.thal0rin.titlebarchanger.Mode;
import net.thal0rin.titlebarchanger.TitlebarChanger;

public class SelectTitleBarTextColor extends SelectColor {
    public SelectTitleBarTextColor(Screen lastScreen, Component title) {
        super(lastScreen, title);
    }

    protected void saveConfig() {
        this.configFile.getConfig().getTitleBarTextColor().setR(this.redValue);
        this.configFile.getConfig().getTitleBarTextColor().setB(this.blueValue);
        this.configFile.getConfig().getTitleBarTextColor().setG(this.greenValue);
        this.configFile.saveConfig();
        TitlebarChanger.mode = Mode.TITLE_BAR_TEXT_COLOR_MODE;
        TitlebarChanger.applyChanges();
    }

    protected void loadConfig() {
        this.redValue = this.configFile.getConfig().getTitleBarTextColor().getR();
        this.greenValue = this.configFile.getConfig().getTitleBarTextColor().getG();
        this.blueValue = this.configFile.getConfig().getTitleBarTextColor().getB();
    }
}
