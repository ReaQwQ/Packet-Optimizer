package com.reaqwq.packetoptimizer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PacketOptimizerConfig {
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(),
            "packet-optimizer.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public boolean aggressiveFlush = true;
    public boolean tcpNoDelay = true;
    public boolean crystalOptimizer = true;
    public boolean inventorySync = true;
    public boolean earlyDecoder = true;

    private static PacketOptimizerConfig INSTANCE;

    public static PacketOptimizerConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    public static PacketOptimizerConfig load() {
        if (!CONFIG_FILE.exists()) {
            PacketOptimizerConfig config = new PacketOptimizerConfig();
            config.save();
            return config;
        }
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            return GSON.fromJson(reader, PacketOptimizerConfig.class);
        } catch (IOException e) {
            return new PacketOptimizerConfig();
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
