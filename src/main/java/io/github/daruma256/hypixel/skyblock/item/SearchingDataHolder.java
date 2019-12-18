package io.github.daruma256.hypixel.skyblock.item;

import io.github.daruma256.hypixel.skyblock.format.AuctionFormat;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchingDataHolder {
    private static HashMap<String, ArrayList<String>> searchingData = new HashMap<>();

    public static boolean contains(@NotNull AuctionFormat format) {
        try {
            return searchingData.get(format.item_name).contains(format.uuid);
        } catch (Exception e) {
            return false;
        }
    }

    @NotNull
    @Contract(pure = true)
    public static Integer size(String itemName) {
        try {
            return searchingData.get(itemName).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isEmpty() {
        return searchingData.isEmpty();
    }

    public static ArrayList<String> getList(String key) {
        try {
            return searchingData.get(key);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void add(@NotNull AuctionFormat format) {
        try {
            searchingData.get(format.item_name).add(format.uuid);
        } catch (NullPointerException e) {
            searchingData.put(format.item_name, new ArrayList<>());
            searchingData.get(format.item_name).add(format.uuid);
        }
    }

    public static boolean containsKey(String key) {
        return searchingData.containsKey(key);
    }

    public static void set(String key, ArrayList<String> list) {
        searchingData.remove(key);
        searchingData.put(key, list);
    }
}
