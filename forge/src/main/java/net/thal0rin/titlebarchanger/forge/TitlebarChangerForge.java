package net.thal0rin.titlebarchanger.forge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import net.thal0rin.titlebarchanger.TitlebarChanger;
import net.thal0rin.titlebarchanger.config.ConfigFile;

import java.io.File;

@Mod(TitlebarChanger.MOD_ID)
public final class TitlebarChangerForge {
    public TitlebarChangerForge() {
        ConfigFile.config_file = new File(FMLPaths.CONFIGDIR.get().toFile(), "titlebar_settings.json");
        TitlebarChanger.init();
    }
}
