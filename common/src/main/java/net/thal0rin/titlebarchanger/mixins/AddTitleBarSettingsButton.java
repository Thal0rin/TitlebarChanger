package net.thal0rin.titlebarchanger.mixins;

import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import net.thal0rin.titlebarchanger.TitlebarChanger;
import net.thal0rin.titlebarchanger.ui.TitleBarSettings;
import net.thal0rin.titlebarchanger.utils.SystemStatus;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Supplier;

@Mixin(OptionsScreen.class)
public abstract class AddTitleBarSettingsButton {

    @Shadow
    @Final
    private Options options;

    @Shadow
    @Final
    private Screen lastScreen;

    @Shadow
    protected abstract Button openScreenButton(Component var1, Supplier<Screen> var2);

    @Inject(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;",
                    ordinal = 4
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    protected void init(CallbackInfo ci, LinearLayout linearLayout, LinearLayout linearLayout2, GridLayout gridLayout, GridLayout.RowHelper rowHelper) {
        if (TitlebarChanger.systemStatus == SystemStatus.SUITABLE && TitlebarChanger.configFile.getConfig().getShowTheMenu()) {
            rowHelper.addChild(this.openScreenButton(Component.translatable("gui.titlebar_settings"), () -> new TitleBarSettings(this.lastScreen)));
        }
    }
}