package com.reaqwq.packetoptimizer.mixin.combat;

import com.reaqwq.packetoptimizer.config.PacketOptimizerConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class AttackSyncMixin {
    @Inject(method = "attack", at = @At("HEAD"))
    private void onAttack(Entity target, CallbackInfo ci) {
        if (target.getWorld().isClient && PacketOptimizerConfig.getInstance().crystalOptimizer
                && target instanceof EndCrystalEntity) {
            target.discard();
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.getNetworkHandler() != null && client.getNetworkHandler().getConnection() != null) {
                client.getNetworkHandler().getConnection().flush();
            }
        }
    }
}
