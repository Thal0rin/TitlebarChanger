package net.thal0rin.titlebarchanger.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.thal0rin.titlebarchanger.TitlebarChanger;
import net.thal0rin.titlebarchanger.ui.WarnScreen;
import net.thal0rin.titlebarchanger.utils.SystemStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class AddWarnScreen {
    @Inject(at = {@At("TAIL")}, method = {"init"})
    protected void init(CallbackInfo ci) {
        if (TitlebarChanger.systemStatus != SystemStatus.SUITABLE && TitlebarChanger.configFile.getConfig().getShowWarnScreen()) {
            Minecraft.getInstance().setScreen(new WarnScreen());
        }
    }
}
