package me.louderdev.zbroken.utils;

import lombok.AllArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.louderdev.zbroken.ZBroken;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;

@AllArgsConstructor
public class PlaceholderUtils {
    private static ZBroken plugin = ZBroken.get();

    public static String parse(OfflinePlayer player, String str) {
        if (str == null) return null;
        if (player == null) return CC.translate(str);

        try {
            if (plugin.isUsingPlaceHolderAPI()) {
                return PlaceholderAPI.setPlaceholders(player, str);
            }
        } catch (NullPointerException ignored) {
            System.out.println(ignored);
        }

        return str;
    }

    public static String parse(String str) {
        return parse(null, str);
    }

}
