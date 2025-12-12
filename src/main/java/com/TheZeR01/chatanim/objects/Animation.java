package com.TheZeR01.chatanim.objects;

import com.TheZeR01.chatanim.utils.ColorUtils;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    private final String name;
    private final int speed;
    private final boolean clearChat;
    private final boolean centered;
    private final String permission; // <--- NUEVO CAMPO
    private final List<String> frames;

    // Constructor actualizado
    public Animation(String name, int speed, boolean clearChat, boolean centered, String permission, List<String> rawFrames) {
        this.name = name;
        this.speed = speed;
        this.clearChat = clearChat;
        this.centered = centered;
        this.permission = permission; // <--- GUARDAMOS EL PERMISO
        this.frames = new ArrayList<>();

        for (String frame : rawFrames) {
            this.frames.add(ColorUtils.colorize(frame));
        }
    }

    public String getName() { return name; }
    public int getSpeed() { return speed; }
    public boolean isClearChat() { return clearChat; }
    public boolean isCentered() { return centered; }
    public String getPermission() { return permission; } // <--- EL GETTER QUE FALTABA
    public List<String> getFrames() { return frames; }
}

