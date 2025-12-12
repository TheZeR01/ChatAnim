package com.TheZeR01.chatanim.utils;
import org.bukkit.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {
    // Patrón para detectar colores HEX: &#RRGGBB
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String colorize(String message) {
        if (message == null) return "";

        // 1. Procesar HEX (Solo versiones 1.16+)
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            try {
                // net.md_5.bungee.api.ChatColor es necesario para HEX
                String hexCode = "#" + matcher.group(1);
                matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(hexCode).toString());
            } catch (NoSuchMethodError e) {
                // Fallback para versiones viejas que no soportan hex
                matcher.appendReplacement(buffer, "");
            }
        }
        matcher.appendTail(buffer);

        // 2. Procesar colores clásicos (&a, &l, etc)
        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }
}