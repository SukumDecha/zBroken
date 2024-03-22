package me.louderdev.zbroken;

import lombok.Getter;

import fr.minuskube.inv.InventoryManager;
import me.louderdev.zbroken.commands.ReclaimCommand;
import me.louderdev.zbroken.listeners.ItemListener;
import me.louderdev.zbroken.listeners.PlayerListener;
import me.louderdev.zbroken.managers.MenuManager;
import me.louderdev.zbroken.managers.PlayerManager;
import me.louderdev.zbroken.runnables.UserCacheRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

@Getter
public final class ZBroken extends JavaPlugin {

    public static ZBroken instance;

    private boolean usingPlaceHolderAPI;
    private PlayerManager playerManager;
    private InventoryManager inventoryManager;
    private MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;
        copyConfig();

        if(getServer().getPluginManager().getPlugin("PlaceHolderAPI") != null) {
            usingPlaceHolderAPI = true;
        }

        //Register Managers
        playerManager = new PlayerManager();
        menuManager = new MenuManager();
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        //Register listeners
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemListener(playerManager), this);

        //Register commands
        getCommand("reclaim").setExecutor(new ReclaimCommand(this));


        //Register runnables
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new UserCacheRunnable(playerManager), 20L, 20 * 60 * 3);

        getLogger().log(Level.SEVERE, "ZBroken has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.SEVERE, "ZBroken has been disabled.");

        long startAt = System.currentTimeMillis();
        for(Player player : Bukkit.getOnlinePlayers()) {
            playerManager.save(player);
        }

        getLogger().log(Level.SEVERE, "Saved player data in " + (System.currentTimeMillis() - startAt) + " ms");
    }


    public static ZBroken get() {
        return instance;
    }

    private void copyConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
