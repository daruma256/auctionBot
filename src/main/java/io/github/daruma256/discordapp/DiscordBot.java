package io.github.daruma256.discordapp;

import io.github.daruma256.config.Config;
import io.github.daruma256.discordapp.listener.JDAListener;
import io.github.daruma256.hypixel.skyblock.format.AuctionFormat;
import io.github.daruma256.mojang.MojangAPI;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

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
        try {
            jda.shutdown();
        } catch (NullPointerException e){
            return;
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

    @NotNull
    public static String formatAuction(@NotNull AuctionFormat format) {
        String name = MojangAPI.getNameFromUUIDString(format.auctioneer);
        assert name != null;
        if (name.equals("Can't get UserName") || name.equals("Unknown Error")) {
            return  "```new Auction!\nbut Can't get User name. sorry.```";
        }
        return  "```new Auction!\nby: " + name + "\nstarting bit: " + format.starting_bid + "\n/ah " + name + "```";
    }
 }
