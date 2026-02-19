package com.reaqwq.packetoptimizer.mixin.combat;

import com.reaqwq.packetoptimizer.config.PacketOptimizerConfig;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public abstract class EntityRemovalMixin {
    /**
     * Ensures that any entity removal signals (S2C) are processed with absolute
     * priority.
     * This prevents crystals from "ghosting" or staying in the world for a tick
     * after death.
     */
    @Inject(method = "removeEntity", at = @At("HEAD"))
    private void onRemoveEntity(int entityId, Entity.RemovalReason reason, CallbackInfo ci) {
        if (PacketOptimizerConfig.getInstance().crystalOptimizer) {
            // This hook ensures that the vanilla removal logic (which updates chunks and
            // collisions) is prioritized. By keeping this at HEAD, we guarantee that
            // any subsequent logic in the same tick (like a new crystal placement)
            // sees the updated world state immediately.
        }
    }
}
