package me.louderdev.zbroken.configs;

import me.louderdev.zbroken.ZBroken;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerConfig {

    private static final File baseFolder = new File(ZBroken.get().getDataFolder(), "userdata");

    public static boolean exists(OfflinePlayer offlinePlayer) {
        return new File(baseFolder, offlinePlayer.getUniqueId() + ".yml").exists();
    }

    public static void create(OfflinePlayer offlinePlayer) {
        if (!baseFolder.exists()) {
            baseFolder.mkdirs();
        }

        File configFile = new File(baseFolder, offlinePlayer.getUniqueId() + ".yml");

        if (!configFile.exists()) {
            createDefaultConfig(configFile, offlinePlayer.getName());
        } else {
            updatePlayerName(configFile, offlinePlayer.getName());
        }
    }

    private static void createDefaultConfig(File file, String playerName) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("name", playerName);
        config.set("storedItem", new String[0]); // Initialize storedItem as an empty array
        config.options().copyDefaults(true);
        saveConfig(file, config);
    }

    private static void updatePlayerName(File file, String playerName) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!config.getString("name").equals(playerName)) {
            config.set("name", playerName);
            saveConfig(file, config);
        }
    }

    private static void saveConfig(File file, FileConfiguration config) {
        try {
            config.save(file);
        } catch (IOException e) {
            ZBroken.get().getLogger().log(Level.WARNING, "Could not save player config: " + e.getMessage());
        }
    }

    public static FileConfiguration getPlayerConfig(UUID uuid) {
        File configFile = new File(baseFolder, uuid + ".yml");
        return YamlConfiguration.loadConfiguration(configFile);
    }


    public static void reloadAllPlayerConfigs() {
        for (OfflinePlayer offlinePlayer : Bukkit.getServer().getOfflinePlayers()) {
            File configFile = new File(baseFolder, offlinePlayer.getUniqueId() + ".yml");
            if (configFile.exists()) {
                reloadPlayerConfig(configFile);
            }
        }
    }

    private static void reloadPlayerConfig(File configFile) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        try {
            config.load(configFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            ZBroken.get().getLogger().log(Level.WARNING, "Could not reload player config: " + e.getMessage());
        }
    }
}
