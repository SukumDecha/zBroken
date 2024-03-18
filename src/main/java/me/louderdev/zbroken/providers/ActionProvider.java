package me.louderdev.zbroken.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class ActionProvider implements InventoryProvider {

    private final ZBroken plugin = ZBroken.get();
    private final ItemStack item;

    @Override
    public void init(Player player, InventoryContents contents) {
        PlayerData data = plugin.getPlayerManager().getPlayerDataByUuid(player.getUniqueId());

        contents.set(1, 2, ClickableItem.of(createOption(true), (e) -> {
            String balanceAsString = PlaceholderUtils.parse(player, "%cmi_user_balance%");
            double playerBalance = Double.valueOf(balanceAsString);

            if (playerBalance - PluginConfig.ITEM_PRICE >= 0) {
                String command = "money take " + player.getName() + " " + PluginConfig.ITEM_PRICE;
                ServerUtils.executeCommand(command);

                data.getStoredItem().remove(item);
                player.getInventory().addItem(item.clone());
                player.sendMessage(CC.translate(PluginConfig.SUCCESS_MESSAGE));
                player.closeInventory();
                return;
            }

            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, 0.2f, 0.2f);
            player.sendMessage(CC.translate(PluginConfig.FAIL_MESSAGE));
        }));

        contents.set(1, 4, ClickableItem.empty(item.clone()));

        contents.set(1, 6, ClickableItem.of(createOption(false), (e) -> {
            data.getStoredItem().remove(item);
            player.sendMessage(CC.translate(PluginConfig.REMOVE_MESSAGE));
            player.closeInventory();
        }));
    }


    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }


    private ItemStack createOption(boolean reclaim) {
        String path = "CONFIG.INVENTORY.ACTION_MENU." + (reclaim ? "RECLAIM" : "REMOVE") + "_BUTTON.";

        ItemBuilder builder = new ItemBuilder(reclaim ? Material.EMERALD : Material.REDSTONE);
        builder.setName(getYamlConfig().getString(path + "NAME"));

        for (String lore : getYamlConfig().getStringList(path + "LORE")) {
            builder.addLoreLine(lore);
        }

        return builder.toItemStack();
    }
}


