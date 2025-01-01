package net.thal0rin.titlebarchanger.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class ConfigFile {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static File config_file;
    private static Config config;

    public void loadConfig() {
        if (config_file.exists()) {
            try (FileReader reader = new FileReader(config_file)) {
                Type type = new TypeToken<Config>() {}.getType();
                config = GSON.fromJson(reader, type);
            } catch (IOException e) {
                e.printStackTrace();
                config = new Config();
            }
        } else {
            config = new Config();

            saveConfig();
        }
    }

    public void saveConfig() {
        try (FileWriter writer = new FileWriter(config_file)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Config getConfig() {
        return config;
    }
}