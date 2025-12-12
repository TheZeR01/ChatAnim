package com.TheZeR01.chatanim.utils;
import org.bukkit.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String colorize(String message) {
        if (message == null) return "";

        // 1. Procesador HEX
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            try {
                String hexCode = "#" + matcher.group(1);
                matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(hexCode).toString());
            } catch (NoSuchMethodError e) {
                matcher.appendReplacement(buffer, "");
            }
        }
        matcher.appendTail(buffer);

        // 2. Procesardor clasico
        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }
}