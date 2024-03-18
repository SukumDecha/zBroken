package me.louderdev.zbroken.commands;

import lombok.RequiredArgsConstructor;
import me.louderdev.zbroken.ZBroken;
import me.louderdev.zbroken.entitys.PlayerData;
import me.louderdev.zbroken.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ReclaimCommand implements CommandExecutor {

    private final ZBroken plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(CC.RED + "Only player can execute this command.");
            return false;
        }

        Player player = (Player) sender;
        PlayerData data = plugin.getPlayerManager().getPlayerDataByUuid(player.getUniqueId());

        if(!data.isLoaded()) {
            plugin.getPlayerManager().load(player);
        }

        plugin.getMenuManager().openBackpack(player);
        return true;
    }
}
