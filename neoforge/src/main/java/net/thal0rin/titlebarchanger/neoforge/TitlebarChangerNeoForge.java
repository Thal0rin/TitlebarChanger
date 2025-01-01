package net.thal0rin.titlebarchanger.neoforge;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.thal0rin.titlebarchanger.TitlebarChanger;
import net.thal0rin.titlebarchanger.config.ConfigFile;

import java.io.File;

@Mod(TitlebarChanger.MOD_ID)
public final class TitlebarChangerNeoForge {
    public TitlebarChangerNeoForge() {
        ConfigFile.config_file = new File(FMLPaths.CONFIGDIR.get().toFile(), "titlebar_settings.json");
        TitlebarChanger.init();
    }
}
