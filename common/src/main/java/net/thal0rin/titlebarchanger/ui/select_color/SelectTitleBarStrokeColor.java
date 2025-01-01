package net.thal0rin.titlebarchanger.ui.select_color;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.thal0rin.titlebarchanger.Mode;
import net.thal0rin.titlebarchanger.TitlebarChanger;

public class SelectTitleBarStrokeColor extends SelectColor {
    public SelectTitleBarStrokeColor(Screen lastScreen, Component title) {
        super(lastScreen, title);
    }

    protected void saveConfig() {
        this.configFile.getConfig().getTitleBarStrokeColor().setR(this.redValue);
        this.configFile.getConfig().getTitleBarStrokeColor().setB(this.blueValue);
        this.configFile.getConfig().getTitleBarStrokeColor().setG(this.greenValue);
        this.configFile.saveConfig();
        TitlebarChanger.mode = Mode.TITLE_BAR_STROKE_COLOR_MODE;
        TitlebarChanger.applyChanges();
    }

    protected void loadConfig() {
        this.redValue = this.configFile.getConfig().getTitleBarStrokeColor().getR();
        this.blueValue = this.configFile.getConfig().getTitleBarStrokeColor().getB();
        this.greenValue = this.configFile.getConfig().getTitleBarStrokeColor().getG();
    }
}
