package me.louderdev.zbroken.utils;

import me.louderdev.zbroken.ZBroken;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class ServerUtils {

    public static void executeCommand(String command) {
        Bukkit.getScheduler().runTask(ZBroken.get(), () -> {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Bukkit.dispatchCommand(console, command);
        });
    }
}
