package io.github.daruma256.hypixel.skyblock;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Tier {
    public static Color getColor(@NotNull String tier) {
        switch (tier) {
            case "UNCOMMON":
                return Color.green;
            case "RARE":
                return Color.blue;
            case "EPIC":
                return new Color(150, 53, 255);
            case "LEGENDARY":
                return Color.orange;
            default:
                return Color.white;
        }
    }
}
