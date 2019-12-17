package io.github.daruma256.hypixel.skyblock;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AOTDDataHolder {
    private static ArrayList<String> aotdList = new ArrayList<>();

    public static boolean contains(String uuid) {
        return aotdList.contains(uuid);
    }

    @NotNull
    @Contract(pure = true)
    public static Integer size() {
        return aotdList.size();
    }

    public static boolean isEmpty() {
        return aotdList.isEmpty();
    }

    public static ArrayList<String> getList() {
        return aotdList;
    }

    public static void add(String uuid) {
        aotdList.add(uuid);
    }

    public static void set(ArrayList<String> list) {
        aotdList = list;
    }
}
