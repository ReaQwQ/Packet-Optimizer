package com.reaqwq.packetoptimizer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.sound.SoundCategory;

public class PacketOptimizer {
    private static final PacketOptimizer INSTANCE = new PacketOptimizer();

    public static PacketOptimizer getInstance() {
        return INSTANCE;
    }

    public void playExplosionEffects(ExplosionS2CPacket packet) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;

        if (world != null) {
            // メインスレッドで実行させる（スレッド安全のため）
            client.execute(() -> {
                double x = packet.center().x;
                double y = packet.center().y;
                double z = packet.center().z;

                // パーティクルの生成
                world.addParticle(packet.explosionParticle(), x, y, z, 1.0, 0.0, 0.0);

                // 爆発音の再生
                world.playSound(x, y, z,
                        packet.explosionSound().value(),
                        SoundCategory.BLOCKS,
                        4.0F,
                        (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F,
                        false);
            });
        }
    }
}