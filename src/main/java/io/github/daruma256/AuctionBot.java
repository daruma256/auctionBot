package io.github.daruma256;

import io.github.daruma256.discordapp.DiscordBot;
import io.github.daruma256.hypixel.skyblock.HypixelAPI;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

//@author daruma_256

public class AuctionBot {
    public static void main(String[] args) {

        DiscordBot.start();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                HypixelAPI.get();
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 1000, 30000);

        Scanner scan = new Scanner(System.in);
        String text = scan.next();

        if (text.equals("stop")) {
            timer.cancel();
            stop();
        }
    }

    public static void stop() {
        System.out.println("[Bot] shutdown started...");
        DiscordBot.stop();
        System.out.println("[Bot] - - - Credit - - -");
        System.out.println("[Bot] API : Hypixel Network (https://api.hypixel.net)");
        System.out.println("[Bot] API : DV8FromTheWorld (https://github.com/DV8FromTheWorld/JDA)");
        System.out.println("[Bot] Coding : daruma_256 (https://github.com/daruma256)");
        System.out.println("[Bot] - - - - - - - - -");
        System.out.println("[Bot] Thank you for Using!");
        System.exit(0);
    }

}
