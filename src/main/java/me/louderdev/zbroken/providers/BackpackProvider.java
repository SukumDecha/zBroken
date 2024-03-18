package me.louderdev.zbroken.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.louderdev.zbroken.ZBroken;
import me.louderdev.zbroken.configs.PluginConfig;
import me.louderdev.zbroken.entitys.PlayerData;
import me.louderdev.zbroken.utils.CC;
import me.louderdev.zbroken.utils.PlaceholderUtils;
import me.louderdev.zbroken.utils.ServerUtils;
import me.louderdev.zbroken.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static me.louderdev.zbroken.configs.PluginConfig.getYamlConfig;

public class BackpackProvider implements InventoryProvider {

    private ZBroken plugin = ZBroken.get();

    @Override
    public void init(Player player, InventoryContents contents) {
        PlayerData data = plugin.getPlayerManager().getPlayerDataByUuid(player.getUniqueId());

        data.getStoredItem().stream().forEach(item -> {
            contents.add(ClickableItem.of(createPreview(item), e -> {
                player.closeInventory();
                plugin.getMenuManager().openAction(player, item);
            }));
        });
    }


    @Override
    public void update(Player player, InventoryContents inventoryContents) {
    }

    private ItemStack createPreview(ItemStack stack) {
        ItemBuilder builder = new ItemBuilder(stack.clone());

        for(String lore : getYamlConfig().getStringList("CONFIG.INVENTORY.BACKPACK_MENU.ITEM_LORE")) {
            builder.addLoreLine(lore);
        }

        return builder.toItemStack();
    }


}
