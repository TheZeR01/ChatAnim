package com.TheZeR01.chatanim.managers;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.TheZeR01.chatanim.objects.Animation;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationManager {
    private final JavaPlugin plugin;
    private final Map<String, Animation> animations = new HashMap<>();

    public AnimationManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadAnimations() {
        animations.clear();
        File folder = new File(plugin.getDataFolder(), "animations");
        if (!folder.exists()) folder.mkdirs();

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;

        for (File file : files) {
            try {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                String name = file.getName().replace(".yml", "");

                int speed = config.getInt("settings.speed", 2);
                boolean clear = config.getBoolean("settings.clear-chat-on-frame", true);
                boolean center = config.getBoolean("settings.vertical-center", true);

                String permission = config.getString("settings.permission");

                List<String> rawFrames = config.getStringList("frames");

                Animation anim = new Animation(name, speed, clear, center, permission, rawFrames);

                animations.put(name, anim);
                plugin.getLogger().info("Animación cargada: " + name);

            } catch (Exception e) {
                plugin.getLogger().severe("Error cargando animación " + file.getName() + ": " + e.getMessage());
            }
        }
    }

    public Animation getAnimation(String name) {
        return animations.get(name);
    }
}
