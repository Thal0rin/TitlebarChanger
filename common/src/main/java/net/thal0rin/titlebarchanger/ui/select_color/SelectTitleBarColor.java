package net.thal0rin.titlebarchanger.ui.select_color;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.thal0rin.titlebarchanger.Mode;
import net.thal0rin.titlebarchanger.TitlebarChanger;

public class SelectTitleBarColor extends SelectColor {
    public SelectTitleBarColor(Screen lastScreen, Component title) {
        super(lastScreen, title);
    }

    protected void saveConfig() {
        this.configFile.getConfig().getTitleBarColor().setR(this.redValue);
        this.configFile.getConfig().getTitleBarColor().setB(this.blueValue);
        this.configFile.getConfig().getTitleBarColor().setG(this.greenValue);
        this.configFile.saveConfig();
        TitlebarChanger.mode = Mode.TITLE_BAR_COLOR_MODE;
        TitlebarChanger.applyChanges();
    }

    protected void loadConfig() {
        this.redValue = this.configFile.getConfig().getTitleBarColor().getR();
        this.blueValue = this.configFile.getConfig().getTitleBarColor().getB();
        this.greenValue = this.configFile.getConfig().getTitleBarColor().getG();
    }
}
