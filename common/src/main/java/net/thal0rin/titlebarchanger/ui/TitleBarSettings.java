package net.thal0rin.titlebarchanger.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.thal0rin.titlebarchanger.Mode;
import net.thal0rin.titlebarchanger.TitlebarChanger;
import net.thal0rin.titlebarchanger.ui.select_color.SelectTitleBarColor;
import net.thal0rin.titlebarchanger.ui.select_color.SelectTitleBarStrokeColor;
import net.thal0rin.titlebarchanger.ui.select_color.SelectTitleBarTextColor;

public class TitleBarSettings extends Screen {
    private final Screen lastScreen;
    public static int theme;
    public static int corner;
    private Component windowThemeText;
    private Component windowCornerText;

    public TitleBarSettings(Screen lastScreen) {
        super(Component.translatable("gui.titlebar_settings"));
        this.lastScreen = lastScreen;
    }

    protected void init() {
        this.updateWindowThemeText();
        this.updateWindowStyleText();
    }

    public void resize(Minecraft p_96575_, int p_96576_, int p_96577_) {
        this.rebuildWidgets();
        super.resize(p_96575_, p_96576_, p_96577_);
    }

    private void updateWindowThemeText() {
        this.windowThemeText = Component.translatable(theme == 1 ? "gui.window_theme_dark" : (theme == 0 ? "gui.window_theme_light" : "gui.window_theme_custom"));
    }

    private void updateWindowStyleText() {
        this.windowCornerText = Component.translatable(corner == 0 ? "gui.window_style_rectangular" : (corner == 1 ? "gui.window_style_default" : "gui.window_style_semi_rounded"));
    }

    private void addButtons() {
        this.addRenderableWidget(Button.builder(this.windowThemeText, (button) -> {
            this.toggleTheme();
            if (TitlebarChanger.configFile.getConfig().getTheme() < 2) {
                TitlebarChanger.mode = Mode.DARK_THEME_MODE;
                TitlebarChanger.applyChanges();
            } else {
                TitlebarChanger.mode = Mode.TITLE_BAR_COLOR_MODE;
                TitlebarChanger.applyChanges();
                TitlebarChanger.mode = Mode.TITLE_BAR_TEXT_COLOR_MODE;
                TitlebarChanger.applyChanges();
                TitlebarChanger.mode = Mode.TITLE_BAR_STROKE_COLOR_MODE;
                TitlebarChanger.applyChanges();
            }

            this.rebuildWidgets();
        }).bounds(this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20).build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (p) -> {
            this.minecraft.setScreen(this.lastScreen);
        }).bounds(this.width / 2 - 100, this.height / 6 + 168, 200, 20).build());
        this.addRenderableWidget(Button.builder(this.windowCornerText, (button) -> {
            this.toggleCorner();
            TitlebarChanger.mode = Mode.CORNER_MODE;
            TitlebarChanger.applyChanges();
            this.rebuildWidgets();
        }).bounds(this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20).build());
        ((Button) this.addRenderableWidget(Button.builder(Component.translatable("gui.set_titlebar_color"), (button) -> {
            Minecraft.getInstance().setScreen(new SelectTitleBarColor(this, Component.translatable("gui.set_titlebar_color")));
        }).bounds(this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20).build())).active = theme == 2;
        ((Button) this.addRenderableWidget(Button.builder(Component.translatable("gui.set_titlebar_text_color"), (button) -> {
            Minecraft.getInstance().setScreen(new SelectTitleBarTextColor(this, Component.translatable("gui.set_titlebar_text_color")));
        }).bounds(this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20).build())).active = theme == 2;
        ((Button) this.addRenderableWidget(Button.builder(Component.translatable("gui.set_titlebar_stroke_color"), (button) -> {
            Minecraft.getInstance().setScreen(new SelectTitleBarStrokeColor(this, Component.translatable("gui.set_titlebar_stroke_color")));
        }).bounds(this.width / 2 - 155, this.height / 6 + 96 - 6, 310, 20).build())).active = theme == 2;
    }

    private void toggleTheme() {
        switch (theme) {
            case 0 -> theme = 2;
            case 1 -> theme = 0;
            case 2 -> theme = 1;
        }

        TitlebarChanger.configFile.getConfig().setTheme(theme);
        TitlebarChanger.configFile.saveConfig();
        this.updateWindowThemeText();
    }

    private void toggleCorner() {
        switch (corner) {
            case 0 -> corner = 1;
            case 1 -> corner = 2;
            case 2 -> corner = 0;
        }

        TitlebarChanger.configFile.getConfig().setCorner(corner);
        this.updateWindowStyleText();
        TitlebarChanger.configFile.saveConfig();
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 40, 16777215);
        this.addButtons();
    }

    static {
        theme = TitlebarChanger.configFile.getConfig().getTheme();
        corner = TitlebarChanger.configFile.getConfig().getCorner();
    }
}
