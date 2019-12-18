package io.github.daruma256.hypixel.skyblock;

import com.google.gson.Gson;
import io.github.daruma256.config.Config;
import io.github.daruma256.discordapp.DiscordBot;
import io.github.daruma256.hypixel.skyblock.format.AuctionFormat;
import io.github.daruma256.hypixel.skyblock.format.RequestFormat;
import io.github.daruma256.hypixel.skyblock.item.SearchingDataHolder;
import io.github.daruma256.hypixel.skyblock.item.SearchingItem;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class HypixelAPI {
    private static final String apiKey = Config.apiKey;
    private static final String baseLink = "https://api.hypixel.net/skyblock/auctions?key=" + apiKey + "&page=";
    private static final ArrayList<String> setupItem = new ArrayList<>();

    public static void get() {
        SearchingItem.addFromQue();
        for (String item : SearchingItem.getList()) {
            if (!SearchingDataHolder.containsKey(item)) {
                setupItem.add(item);
            }
        }
        if (SearchingItem.getList().isEmpty()) {
            System.out.println("[Bot] find Item is't setting");
            return;
        }
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
                        if (SearchingItem.getList().contains(auctionFormat.item_name)) {
                            if (!setupItem.contains(auctionFormat.item_name)) {
                                if (!SearchingDataHolder.contains(auctionFormat)) {
                                    System.out.println("[Bot] " + auctionFormat.item_name + " Find!");
                                    DiscordBot.sendMessage(auctionFormat);
                                }
                            }
                            SearchingDataHolder.add(auctionFormat);
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
        for (String string : setupItem) {
            System.out.println("[Bot] " + SearchingDataHolder.getList(string).size() + " " +  string + " Find!");
        }
        setupItem.clear();
    }

    private static InputStream getAuctions(Integer page) throws IOException {
        URL url = new URL(baseLink + page);
        return url.openConnection().getInputStream();
    }
}
