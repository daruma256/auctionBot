package io.github.daruma256.hypixel.skyblock;

import com.google.gson.Gson;
import io.github.daruma256.config.Config;
import io.github.daruma256.discordapp.DiscordBot;
import io.github.daruma256.hypixel.skyblock.format.AuctionFormat;
import io.github.daruma256.hypixel.skyblock.format.RequestFormat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class HypixelAPI {
    private static final String apiKey = Config.apiKey;
    private static final String baseLink = "https://api.hypixel.net/skyblock/auctions?key=" + apiKey + "&page=";

    public static void get() {
        if (AOTDDataHolder.isEmpty()) {
            setUp();
            System.out.println("[Bot] new AOTD find Start...");
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 1;; i++){
            try {
                InputStream inputStream = getAuctions(i);
                RequestFormat request = new Gson().fromJson(new InputStreamReader(inputStream), RequestFormat.class);
                inputStream.close();

                if (request.totalPages == 0) {
                    break;
                }

                if (request.success.equals("true")) {
                    for (AuctionFormat auctionFormat : request.auctions) {
                        if (auctionFormat.item_name.contains("Aspect of the Dragons")) {
                            if (!AOTDDataHolder.contains(auctionFormat.uuid)) {
                                System.out.println("[Bot] Find!");
                                DiscordBot.sendMessage(DiscordBot.formatAuction(auctionFormat));
                            }
                            arrayList.add(auctionFormat.uuid);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        AOTDDataHolder.set(arrayList);
    }

    private static void setUp() {
        System.out.println("[Bot] SetUp started!");
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 1;; i++){
            try {
                InputStream inputStream = getAuctions(i);
                RequestFormat request = new Gson().fromJson(new InputStreamReader(inputStream), RequestFormat.class);
                inputStream.close();
                if (request.totalPages == 0) {
                    break;
                }

                DecimalFormat format = new DecimalFormat("##00");
                if (request.success.equals("true")) {
                    for (AuctionFormat auctionFormat : request.auctions) {
                        if (auctionFormat.item_name.contains("Aspect of the Dragons")) {
                            arrayList.add(auctionFormat.uuid);
                        }
                    }
                    System.out.println("[Bot] "  + format.format(i) + "Page Loaded");
                } else {
                    System.out.println("[Bot] Can't get Auction Data At Page: " + format.format(i) );
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        AOTDDataHolder.set(arrayList);
        System.out.println("[Bot] SetUp success!");
        System.out.println("[Bot] " + AOTDDataHolder.size() + "AOTD find.");
    }

    private static InputStream getAuctions(Integer page) throws IOException {
        URL url = new URL(baseLink + page);
        return url.openConnection().getInputStream();
    }
}
