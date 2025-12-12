package com.TheZeR01.chatanim;
import com.TheZeR01.chatanim.commands.AnimCommand;
import com.TheZeR01.chatanim.managers.AnimationManager;

import org.bukkit.plugin.java.JavaPlugin;

public final class ChatAnim extends JavaPlugin {
    private AnimationManager animationManager;

    @Override
    public void onEnable() {
        // 1. Cargar Configuración predeterminada (config.yml)
        saveDefaultConfig();

        // 2. Inicializar Managers
        this.animationManager = new AnimationManager(this);

        // 3. Cargar Animaciones desde la carpeta
        this.animationManager.loadAnimations();

        // 4. Registrar Comandos
        // Asegúrate de que "chatanim" esté en tu plugin.yml
        if (getCommand("chatanim") != null) {
            getCommand("chatanim").setExecutor(new AnimCommand(this));
        } else {
            getLogger().severe("¡Olvidaste registrar el comando 'chatanim' en el plugin.yml!");
        }

        getLogger().info("ChatAnimator activado correctamente.");
    }

    @Override
    public void onDisable() {
        // Bukkit se encarga de cancelar las tareas (tasks),
        // pero aquí podrías guardar datos si fuera necesario.
        getLogger().info("ChatAnimator desactivado.");
    }

    // Getter para que otras clases (como los comandos) accedan al manager
    public AnimationManager getAnimationManager() {
        return animationManager;
    }
}
