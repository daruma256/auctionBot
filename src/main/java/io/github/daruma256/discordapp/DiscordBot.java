package io.github.daruma256.discordapp;

import io.github.daruma256.config.Config;
import io.github.daruma256.discordapp.listener.JDAListener;
import io.github.daruma256.hypixel.skyblock.Tier;
import io.github.daruma256.hypixel.skyblock.format.AuctionFormat;
import io.github.daruma256.mojang.MojangAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;

public class DiscordBot {
    private static JDA jda;

    public static void start() {
        try {
            jda = new JDABuilder(Config.discordBotToken).setActivity(Activity.playing("Searching Auction")).build();
            jda.addEventListener(new JDAListener());
        }catch (LoginException e) {
            System.out.println("[Bot] Login Failed");
        }
    }

    public static void stop() {
        assert jda != null;
        try {
            jda.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String message) {
        Guild guild = jda.getGuildById(Config.discordServerID);
        assert guild != null : "Not Found Guild<" + Config.discordServerID + ">";
        TextChannel channel = guild.getTextChannelById(Config.discordTextChannel);
        assert channel != null : "Not Found TextChannel<" + Config.discordTextChannel + ">";
        if (channel.canTalk()) {
            channel.sendMessage(message).queue();
        }
    }

    public static void sendMessage(AuctionFormat format) {
        Guild guild = jda.getGuildById(Config.discordServerID);
        assert guild != null : "Not Found Guild<" + Config.discordServerID + ">";
        TextChannel channel = guild.getTextChannelById(Config.discordTextChannel);
        assert channel != null : "Not Found TextChannel<" + Config.discordTextChannel + ">";

        if (channel.canTalk()) {
            String userName = MojangAPI.getNameFromUUIDString(format.auctioneer);
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("new " + format.item_name + "!");
            builder.setDescription("/ah " + userName + "\n\nstarting bit: " + format.starting_bid);
            builder.setColor(Tier.getColor(format.tier));
            channel.sendMessage(builder.build()).queue();
        }
    }
 }
