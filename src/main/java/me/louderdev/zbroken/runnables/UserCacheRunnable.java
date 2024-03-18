package me.louderdev.zbroken.runnables;

import lombok.RequiredArgsConstructor;
import me.louderdev.zbroken.entitys.PlayerData;
import me.louderdev.zbroken.managers.PlayerManager;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class UserCacheRunnable implements Runnable {

    private final PlayerManager playerManager;

    @Override
    public void run() {
        Set<UUID> toRemove = findOfflinePlayers();
        removeOfflinePlayers(toRemove);
    }

    private Set<UUID> findOfflinePlayers() {
        Set<UUID> toRemove = new HashSet<>();
        for (Map.Entry<UUID, PlayerData> entry : playerManager.getDataMap().entrySet()) {
            if (Bukkit.getPlayer(entry.getKey()) == null) {
                toRemove.add(entry.getKey());
            }
        }
        return toRemove;
    }

    private void removeOfflinePlayers(Set<UUID> toRemove) {
        for (UUID uuid : toRemove) {
            playerManager.getDataMap().remove(uuid);
        }
    }
}
