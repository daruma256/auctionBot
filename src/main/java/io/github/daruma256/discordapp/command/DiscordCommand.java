package io.github.daruma256.discordapp.command;

import io.github.daruma256.discordapp.DiscordBot;
import io.github.daruma256.hypixel.skyblock.item.SearchingItem;

public class DiscordCommand {
    public static void onCommand(String command) {
        if (command.startsWith("!search")) {
            if (command.startsWith("!search add ")) {
                String name = command.replace("!search add ", "");
                SearchingItem.addQue(name);
            } else if (command.startsWith("!search remove ")) {
                String name = command.replace("!search remove ", "");
                SearchingItem.remove(name);
            }
        } else if (command.startsWith("!ping")) {
            DiscordBot.sendMessage("pong!");
            DiscordBot.loadConfigFromTopic();
        }
    }
}
