package com.TheZeR01.chatanim;
import com.TheZeR01.chatanim.commands.AnimCommand;
import com.TheZeR01.chatanim.managers.AnimationManager;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatAnim extends JavaPlugin {
    private AnimationManager animationManager;

    private void registerCommand(String name, AnimCommand commandExecutor) {
        if (this.getCommand(name) != null) {
            this.getCommand(name).setExecutor(commandExecutor);
        } else {
            getLogger().severe("Error: El comando '" + name + "' no está definido en plugin.yml");
        }
    }

    private void registerListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void onEnable() {
        // 3. Inicialización limpia
        getLogger().info("Iniciando ChatAnimator...");

        // Cargar Configuración
        this.saveDefaultConfig();

        // Instanciar Managers
        this.animationManager = new AnimationManager(this);

        // Cargar Datos
        this.animationManager.loadAnimations();

        // Registrar Comandos
        this.registerCommand("chatanim", new AnimCommand(this));

        // Registrar Eventos
        // this.eventListener(new EventListener(this));

        getLogger().info("¡ChatAnimator cargado correctamente!");
    }

    @Override
    public void onDisable() {
        this.animationManager = null;
        getLogger().info("ChatAnimator desactivado.");
    }

    // 4. Getters Seguros
    public AnimationManager getAnimationManager() {
        return this.animationManager;
    }
}
