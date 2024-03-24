package me.louderdev.zbroken.listeners;

import lombok.RequiredArgsConstructor;
import me.louderdev.zbroken.ZBroken;
import me.louderdev.zbroken.configs.PluginConfig;
import me.louderdev.zbroken.entitys.PlayerData;
import me.louderdev.zbroken.managers.PlayerManager;
import me.louderdev.zbroken.utils.CC;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
public class ItemListener implements Listener {

    private final PlayerManager playerManager;
    private final Set<Material> allowedMats = PluginConfig.getAllowedMaterial();

    @EventHandler
    public void onItemBroke(PlayerItemBreakEvent e) {
        Player player = e.getPlayer();
        ItemStack stack = e.getBrokenItem().clone();

        if (stack == null) return;
        if(!isAllowed(stack)) return;

        player.getInventory().remove(stack);
        stack.setDurability((short) 0);

        PlayerData data = playerManager.getPlayerDataByUuid(player.getUniqueId());
        data.getStoredItem().add(stack.clone());
        playerManager.saveAsync(player);

        player.sendMessage(CC.translate(PluginConfig.ITEM_BROKE_MESSAGE));
    }


    private boolean isAllowed(ItemStack stack) {
        if(!allowedMats.contains(stack.getType())) return false;
        if(stack.getItemMeta().hasLore()) {
            List<String> lore = new ArrayList<>(stack.getItemMeta().getLore());
            for (String str : lore) {
                if(ChatColor.stripColor(str).contains(ChatColor.stripColor("Restore"))) {
                    return false;
                }
            }
        }


        return true;
    }




}
