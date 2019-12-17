package io.github.daruma256.mojang;

import com.google.gson.Gson;
import io.github.daruma256.mojang.cache.PlayerCache;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MojangAPI {
    private static final String session = "https://sessionserver.mojang.com/session/minecraft/profile/";

    @Nullable
    public static String getNameFromUUIDString(String uuid) {
        if (PlayerCache.containsKey(uuid)) {
            return PlayerCache.get(uuid);
        }
        try {
            URL url = new URL(session + uuid);
            InputStream inputStream = url.openConnection().getInputStream();
            ProfileFormat format = new Gson().fromJson(new InputStreamReader(inputStream), ProfileFormat.class);
            inputStream.close();

            PlayerCache.add(uuid, format.name);
            return format.name;
        } catch (IOException e) {
            return "Can't get UserName";
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Error";
        }
    }
}
