package me.louderdev.zbroken.configs;

import me.louderdev.zbroken.ZBroken;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PluginConfig {

    private static final File configFile = new File(ZBroken.get().getDataFolder(), "config.yml");

    public static YamlConfiguration getYamlConfig() {
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public static double ITEM_PRICE = getYamlConfig().getDouble("CONFIG.ITEM_PRICE");

    public static String ITEM_BROKE_MESSAGE = getYamlConfig().getString("MESSAGE.ITEM_BROKE");
    public static String SUCCESS_MESSAGE = getYamlConfig().getString("MESSAGE.SUCCESS");
    public static String FAIL_MESSAGE = getYamlConfig().getString("MESSAGE.FAIL");
    public static String REMOVE_MESSAGE = getYamlConfig().getString("MESSAGE.REMOVE");

    public static List<String> allowedMaterialAsString = getYamlConfig().getStringList("CONFIG.ALLOWED_ITEM");


    public static Set<Material> getAllowedMaterial() {
        Set<Material> allowedMats = new HashSet<>();
        for(String mat : allowedMaterialAsString) {
            try {
                allowedMats.add(Material.valueOf(mat));
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("Coun't parased material: " + mat);
            }
        }

        return allowedMats;
    }
}
