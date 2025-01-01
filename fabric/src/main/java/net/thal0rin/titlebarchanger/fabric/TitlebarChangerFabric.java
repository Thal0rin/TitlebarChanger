package net.thal0rin.titlebarchanger.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.thal0rin.titlebarchanger.TitlebarChanger;
import net.thal0rin.titlebarchanger.config.ConfigFile;

import java.io.File;

public final class TitlebarChangerFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigFile.config_file = new File(FabricLoader.getInstance().getConfigDirectory(), "titlebar_settings.json");
        TitlebarChanger.init();
    }
}
