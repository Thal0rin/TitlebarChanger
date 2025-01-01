package net.thal0rin.titlebarchanger.mixins;

import com.llamalad7.mixinextras.sugar.Local;

import java.util.function.Supplier;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.thal0rin.titlebarchanger.TitlebarChanger;
import net.thal0rin.titlebarchanger.ui.TitleBarSettings;
import net.thal0rin.titlebarchanger.utils.SystemStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(OptionsScreen.class)
public abstract class AddTitleBarSettingsButton {
   @Shadow
   protected abstract Button openScreenButton(Component component, Supplier<Screen> supplier);

   @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/OptionsScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 5),  locals = LocalCapture.CAPTURE_FAILHARD)
   private void addTitleBarSettingsButton(CallbackInfo ci, GridLayout gridLayout, GridLayout.RowHelper rowHelper) {
      OptionsScreen optionsScreen = (OptionsScreen) (Object) this;
      if (TitlebarChanger.systemStatus == SystemStatus.SUITABLE && TitlebarChanger.configFile.getConfig().getShowTheMenu()) {
         rowHelper.addChild(this.openScreenButton(Component.translatable("gui.titlebar_settings"), () -> new TitleBarSettings(optionsScreen)));
      }
   }
}