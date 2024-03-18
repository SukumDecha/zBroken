package me.louderdev.zbroken.entitys;

import lombok.Getter;
import lombok.Setter;
import me.louderdev.zbroken.ZBroken;
import me.louderdev.zbroken.configs.PlayerConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Getter @Setter
public class PlayerData {

    private UUID uuid;
    private String name;
    private List<ItemStack> storedItem;


    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.storedItem = new ArrayList<>();
    }

    public void load() {
        FileConfiguration fileConfiguration = PlayerConfig.getPlayerConfig(uuid);
        this.name = fileConfiguration.getString("name");

        if (fileConfiguration.contains("storedItem")) {
            this.storedItem = (List<ItemStack>) fileConfiguration.get("storedItem");
        }
    }

    private void save() {
        File file = new File(ZBroken.get().getDataFolder(), "userdata/" + uuid + ".yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("name", name);

        if(storedItem.size() > 0) {
            configuration.set("storedItem", storedItem);
        }

        try {
            configuration.save(file);
        } catch (IOException e) {
            ZBroken.get().getLogger().warning("Could not save player config: " + name);
        }
    }
}
