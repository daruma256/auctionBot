package io.github.daruma256.mojang.cache;

import java.util.HashMap;

public class PlayerCache {
    private static HashMap<String, String> uuidMap = new HashMap<>();

    public static void add(String key, String value) {
        uuidMap.put(key, value);
    }

    public static String get(String key) {
        return uuidMap.get(key);
    }

    public static boolean containsKey(String key) {
        return uuidMap.containsKey(key);
    }
}
