package io.github.daruma256.hypixel.skyblock.item;

import io.github.daruma256.discordapp.DiscordBot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchingItem {
    private static ArrayList<String> list = new ArrayList<>();
    private static ArrayList<String> que = new ArrayList<>();

    public static boolean isSearching(String itemName) {
        return list.contains(itemName);
    }

    public static ArrayList<String> getList() {
        return list;
    }

    public static boolean isEmpty() {
        return list.isEmpty();
    }

    public static void add(String itemName) {
        if (!list.contains(itemName)) {
            list.add(itemName);
            DiscordBot.addChannelTopic(itemName + "\n");
        }
    }

    public static void add(@NotNull String[] itemNames) {
        for (String itemName : itemNames) {
            if (!list.contains(itemName)) {
                if (itemName.isEmpty()) {
                    continue;
                }
                list.add(itemName);
            }
        }
    }

    public static void addFromQue() {
        list.addAll(que);
        que.clear();
    }

    public static void addQue(String itemName) {
        if (!list.contains(itemName)) {
            if (itemName.isEmpty()) {
                return;
            }
            que.add(itemName);
            DiscordBot.addChannelTopic("\n" + itemName);
        }
    }

    public static void addQue(@NotNull String[] itemNames) {
        for (String itemName : itemNames) {
            if (itemName.isEmpty()) {
                continue;
            }
            if (!list.contains(itemName)) {
                que.add(itemName);
                DiscordBot.addChannelTopic("\n" + itemName);
            }
        }
    }

    public static void remove(String itemName) {
        list.remove(itemName);
        que.remove(itemName);
        DiscordBot.removeContentFromTopic(itemName);
    }
}
