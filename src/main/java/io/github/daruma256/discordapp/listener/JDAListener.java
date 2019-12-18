package io.github.daruma256.discordapp.listener;

import io.github.daruma256.discordapp.DiscordBot;
import io.github.daruma256.discordapp.command.DiscordCommand;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class JDAListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            if (event.getMessage().getContentRaw().startsWith("!")) {
                DiscordCommand.onCommand(event.getMessage().getContentRaw());
            }
        }
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        DiscordBot.loadConfigFromTopic();
    }
}
