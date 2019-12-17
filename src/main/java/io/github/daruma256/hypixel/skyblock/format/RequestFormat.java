package io.github.daruma256.hypixel.skyblock.format;

public class RequestFormat {
    public String success;
    public int totalPages;
    public int totalAuctions;
    public long lastUpdated;
    public AuctionFormat[] auctions;
}