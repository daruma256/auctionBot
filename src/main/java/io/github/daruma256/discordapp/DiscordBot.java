package io.github.daruma256.discordapp;

import io.github.daruma256.config.Config;
import io.github.daruma256.discordapp.listener.JDAListener;
import io.github.daruma256.hypixel.skyblock.Tier;
import io.github.daruma256.hypixel.skyblock.enchantment.SBEnchantment;
import io.github.daruma256.hypixel.skyblock.format.AuctionFormat;
import io.github.daruma256.hypixel.skyblock.item.SearchingItem;
import io.github.daruma256.mojang.MojangAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

public class DiscordBot {
    private static JDA jda;

    public static void start() {
        try {
            jda = new JDABuilder(Config.discordBotToken).setActivity(Activity.playing("Searching Auction")).build();
            jda.addEventListener(new JDAListener());
        }catch (LoginException e) {
            System.out.println("[Bot] Login Failed");
        }catch (NullPointerException e) {
            e.printStackTrace();
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
        assert guild != null;
        TextChannel channel = guild.getTextChannelById(Config.discordTextChannel);
        assert channel != null;

        if (channel.canTalk()) {
            channel.sendMessage(message).queue();
        }
    }

    public static void sendMessage(AuctionFormat format) {
        Guild guild = jda.getGuildById(Config.discordServerID);
        assert guild != null;
        TextChannel channel = guild.getTextChannelById(Config.discordTextChannel);
        assert channel != null;

        if (channel.canTalk()) {
            String userName = MojangAPI.getNameFromUUIDString(format.auctioneer);
            if (SBEnchantment.has(format.item_lore)) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("new " + format.item_name + "!");
                builder.setDescription("*The Item has Enchantment\n/ah " + userName + "\n\nstarting bit: " + format.starting_bid);
                builder.setColor(Tier.getColor(format.tier));
                channel.sendMessage(builder.build()).queue();
                return;
            }
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("new " + format.item_name + "!");
            builder.setDescription("/ah " + userName + "\n\nstarting bit: " + format.starting_bid);
            builder.setColor(Tier.getColor(format.tier));
            channel.sendMessage(builder.build()).queue();
        }
    }

    public static void addChannelTopic(String topic) {
        Guild guild = jda.getGuildById(Config.discordServerID);
        assert guild != null;
        TextChannel channel = guild.getTextChannelById(Config.discordTextChannel);
        assert channel != null;

        String defaultTopic = channel.getTopic();
        if (hasContentAtTopic(topic)) {
            return;
        }
        if (defaultTopic == null) {
            defaultTopic = "---Searching List---";
        }
        if (defaultTopic.isEmpty()) {
            defaultTopic = "---Searching List---";
        }

        channel.getManager().setTopic(defaultTopic + "\n" + topic).queue();
    }

    public static boolean hasContentAtTopic(String content) {
        Guild guild = jda.getGuildById(Config.discordServerID);
        assert guild != null;
        TextChannel channel = guild.getTextChannelById(Config.discordTextChannel);
        assert channel != null;

        String defaultTopic = channel.getTopic();
        if (defaultTopic == null) {
            channel.getManager().setTopic("---Searching List---").queue();
            return false;
        }
        return defaultTopic.contains(content);
    }

    public static void clearChannelTopic() {
        Guild guild = jda.getGuildById(Config.discordServerID);
        assert guild != null;
        TextChannel channel = guild.getTextChannelById(Config.discordTextChannel);
        assert channel != null;

        channel.getManager().setTopic("---Searching List---").queue();
    }

    public static void removeContentFromTopic(String content) {
        Guild guild = jda.getGuildById(Config.discordServerID);
        assert guild != null;
        TextChannel channel = guild.getTextChannelById(Config.discordTextChannel);
        assert channel != null;

        String defaultTopic = channel.getTopic();
        if (defaultTopic == null) {
            defaultTopic = "---Searching List---";
        }
        channel.getManager().setTopic(defaultTopic.replace("\n" + content, "")).queue();
        System.out.println("[Bot] " + SearchingItem.getList() + "removed to Search.");
    }

    public static void loadConfigFromTopic() {
        Guild guild = jda.getGuildById(Config.discordServerID);
        assert guild != null;
        TextChannel channel = guild.getTextChannelById(Config.discordTextChannel);
        assert channel != null;

        String defaultTopic = channel.getTopic();
        if (defaultTopic == null) {
            clearChannelTopic();
            return;
        }
        if (defaultTopic.isEmpty()) {
            clearChannelTopic();
            return;
        }
        String items = defaultTopic.replace("---Searching List---" + "\n", "");
        if (items.isEmpty()) {
            return;
        }
        String[] list = items.split("\n");
        SearchingItem.addQue(list);
        System.out.println("[Bot] " + Arrays.asList(list) + "from Topic.");
    }
 }
