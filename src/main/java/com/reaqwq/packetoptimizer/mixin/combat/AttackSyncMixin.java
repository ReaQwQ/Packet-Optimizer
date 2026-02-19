package com.reaqwq.packetoptimizer.mixin.combat;

import com.reaqwq.packetoptimizer.config.PacketOptimizerConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class AttackSyncMixin {
    @Inject(method = "attack", at = @At("HEAD"))
    private void onAttack(Entity target, CallbackInfo ci) {
        if (PacketOptimizerConfig.getInstance().crystalOptimizer && target instanceof EndCrystalEntity) {
            target.discard();
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.getNetworkHandler() != null) {
                client.getNetworkHandler().getConnection().flush();
            }
        }
    }
}
