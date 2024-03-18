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

    private final UUID uuid;
    private String name;
    private boolean loaded;
    private List<ItemStack> storedItem;


    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.storedItem = new ArrayList<>();
    }

}
