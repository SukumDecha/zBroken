package me.louderdev.zbroken.managers;

import fr.minuskube.inv.SmartInventory;
import me.louderdev.zbroken.providers.ActionProvider;
import me.louderdev.zbroken.providers.BackpackProvider;
import me.louderdev.zbroken.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static me.louderdev.zbroken.configs.PluginConfig.getYamlConfig;

public class MenuManager {

    public static SmartInventory BACKPACK_MENU = SmartInventory.builder()
                .provider(new BackpackProvider())
                .size(3, 9)
                .title(CC.translate(getYamlConfig().getString("CONFIG.INVENTORY.BACKPACK_MENU.TITLE")))
                .build();


    public SmartInventory getActionMenu(ItemStack stack) {
        return  SmartInventory.builder()
                .provider(new ActionProvider(stack))
                .size(3, 9)
                .title(CC.translate(getYamlConfig().getString("CONFIG.INVENTORY.ACTION_MENU.TITLE")))
                .build();
    }
    public void openBackpack(Player player) {
        BACKPACK_MENU.open(player);
    }

    public void openAction(Player player, ItemStack stack) {
        getActionMenu(stack).open(player);
    }

}
