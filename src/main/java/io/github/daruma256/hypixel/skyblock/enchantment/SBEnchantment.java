package io.github.daruma256.hypixel.skyblock.enchantment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class SBEnchantment {
    public static final HashMap<String, ArrayList<String>> EnchantMentMap = getEnchantmentMap();

    public static boolean has(String lore) {
        for (ArrayList<String> list : EnchantMentMap.values()) {
            for (String ench : list) {
                if (lore.contains(ench)) {
                    return true;
                }
            }
        }
        return false;
    }

    @NotNull
    private static HashMap<String, ArrayList<String>> getEnchantmentMap() {
        HashMap<String, ArrayList<String>> map = new HashMap<>();

        ArrayList<String> swordEnchantment = new ArrayList<>();
        for (Sword sword : Sword.values()) {
            swordEnchantment.add(sword.name().replace("_", " "));
        }
        map.put("Sword", swordEnchantment);
        return map;
    }


    private enum Sword{
        Cleave,
        Critical,
        Cubism,
        Ender_Slayer,
        Execute,
        Experience,
        First_Strike,
        Giant_Killer,
        Impaling,
        Lethality,
        Life_Steal,
        Luck,
        Looting,
        Scavenger,
        Thunderlord,
        Vampirism,
        Venomous,
        Telekinesis
    }
}
