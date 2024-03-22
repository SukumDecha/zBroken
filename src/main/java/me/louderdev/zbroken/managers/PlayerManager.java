package me.louderdev.zbroken.managers;

import lombok.Getter;
import me.louderdev.zbroken.ZBroken;
import me.louderdev.zbroken.configs.PlayerConfig;
import me.louderdev.zbroken.entitys.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    @Getter private final Map<UUID, PlayerData> dataMap = new HashMap<>();

    public PlayerData getPlayerDataByUuid(UUID uuid) {
        return dataMap.computeIfAbsent(uuid, PlayerData::new);
    }

    public PlayerData load(Player player) {
        FileConfiguration fileConfiguration = PlayerConfig.getPlayerConfig(player.getUniqueId());
        PlayerData data = getPlayerDataByUuid(player.getUniqueId());


        if(data.isLoaded()) {
            Bukkit.broadcastMessage(data.getStoredItem().toString());
            return data;
        }

        if(fileConfiguration.contains("name")) {
            data.setName(fileConfiguration.getString("name"));
        } else {
            data.setName(player.getName());
        }


        if (fileConfiguration.contains("storedItem")) {
            data.setStoredItem((List<ItemStack>) fileConfiguration.getList("storedItem"));
        }

        data.setLoaded(true);
        return data;
    }

    public PlayerData save(Player player) {
        File file = new File(ZBroken.get().getDataFolder(), "userdata/" + player.getUniqueId() + ".yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        PlayerData data = getPlayerDataByUuid(player.getUniqueId());

        configuration.set("name", data.getName());
        configuration.set("storedItem", data.getStoredItem());

        try {
            configuration.save(file);
            ZBroken.get().getLogger().warning("Saving data: " + data.getName() + " | " + data.getStoredItem().toString());
        } catch (IOException e) {
            ZBroken.get().getLogger().warning("Could not save player config: " + data.getName());
        }

        return data;
    }

    public void saveAsync(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(ZBroken.get(), () -> {
            save(player);
        });
    }
}
