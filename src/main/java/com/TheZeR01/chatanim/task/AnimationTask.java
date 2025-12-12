package com.TheZeR01.chatanim.task;
import com.TheZeR01.chatanim.objects.Animation;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import com.TheZeR01.chatanim.utils.ColorUtils;

public class AnimationTask extends BukkitRunnable {
    private final Player player;
    private final Animation animation;
    private final String endMessage; // Mensaje configurable del config.yml
    private int currentFrame = 0;

    public AnimationTask(Player player, Animation animation, String endMessage) {
        this.player = player;
        this.animation = animation;
        this.endMessage = endMessage;
    }

    @Override
    public void run() {
        if (!player.isOnline() || currentFrame >= animation.getFrames().size()) {
            this.cancel();
            // Mensaje final configurable
            if (endMessage != null && !endMessage.isEmpty()) {
                // Limpieza final opcional
                for (int i = 0; i < 100; i++) player.sendMessage("");
                player.sendMessage(ColorUtils.colorize(endMessage));
            }
            return;
        }

        // Limpiar chat si está activado en la anim
        if (animation.isClearChat()) {
            for (int i = 0; i < 100; i++) player.sendMessage("");
        }

        // Centrado vertical (relleno arriba)
        if (animation.isCentered()) {
            player.sendMessage("\n\n\n\n");
        }

        // Enviar el frame actual
        player.sendMessage(animation.getFrames().get(currentFrame));

        // Centrado vertical (relleno abajo)
        if (animation.isCentered()) {
            player.sendMessage("\n\n\n\n");
        }

        currentFrame++;
    }
}
