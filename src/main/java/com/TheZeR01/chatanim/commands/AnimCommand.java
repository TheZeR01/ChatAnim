package com.TheZeR01.chatanim.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.TheZeR01.chatanim.ChatAnim;
import com.TheZeR01.chatanim.objects.Animation;
import com.TheZeR01.chatanim.task.AnimationTask;
import com.TheZeR01.chatanim.utils.ColorUtils;

public class AnimCommand implements CommandExecutor {

    private final ChatAnim plugin;

    public AnimCommand(ChatAnim plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Validación básica de argumentos
        if (args.length == 0) {
            sender.sendMessage(ColorUtils.colorize("&cUso: /chatanim <play|reload> [args...]"));
            return true;
        }

        String subCommand = args[0].toLowerCase();

        // --- SUBCOMANDO: RELOAD ---
        if (subCommand.equals("reload")) {
            if (!sender.hasPermission("chatanim.admin")) {
                sender.sendMessage(ColorUtils.colorize("&cNo tienes permiso."));
                return true;
            }
            plugin.reloadConfig();
            plugin.getAnimationManager().loadAnimations();
            sender.sendMessage(ColorUtils.colorize("&aConfiguración y animaciones recargadas."));
            return true;
        }

        // --- SUBCOMANDO: PLAY ---
        if (subCommand.equals("play")) {
            // Estructura: /chatanim play <jugador> <animacion>
            if (args.length < 3) {
                sender.sendMessage(ColorUtils.colorize("&cUso: /chatanim play <jugador> <animacion>"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ColorUtils.colorize("&cEl jugador no está en línea."));
                return true;
            }

            String animName = args[2];
            Animation anim = plugin.getAnimationManager().getAnimation(animName);

            if (anim == null) {
                sender.sendMessage(ColorUtils.colorize("&cLa animación '" + animName + "' no existe."));
                return true;
            }

            // Verificar permisos específicos de la animación (definido en el YML de la animación)
            // Si el sender es consola, ignoramos permisos. Si es jugador, verificamos.
            if (sender instanceof Player && anim.getPermission() != null && !anim.getPermission().isEmpty()) {
                if (!sender.hasPermission((String) anim.getPermission())) {
                    sender.sendMessage(ColorUtils.colorize("&cNo tienes permiso para usar esta animación."));
                    return true;
                }
            }

            // Obtener el mensaje final desde config.yml
            String endMsg = plugin.getConfig().getString("messages.end-animation", "");

            // INICIAR LA TAREA
            // anim.getSpeed() define los ticks (velocidad)
            new AnimationTask(target, anim, endMsg).runTaskTimer(plugin, 0L, anim.getSpeed());

            sender.sendMessage(ColorUtils.colorize("&aReproduciendo animación &e" + animName + "&a para &e" + target.getName()));
            return true;
        }

        return true;
    }
}
