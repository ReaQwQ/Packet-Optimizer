package com.reaqwq.packetoptimizer.mixin.inventory;

import com.reaqwq.packetoptimizer.config.PacketOptimizerConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public class InventorySyncMixin {
    @Inject(method = "onMouseClick", at = @At("TAIL"))
    private void onSlotClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if (PacketOptimizerConfig.getInstance().inventorySync) {
            this.flushConnection();
        }
    }

    @Inject(method = "handleHotbarKeyPressed", at = @At("TAIL"))
    private void onHotbarKeyPressed(int keyCode, int scanCode, CallbackInfoReturnable<Boolean> cir) {
        if (PacketOptimizerConfig.getInstance().inventorySync) {
            this.flushConnection();
        }
    }

    private void flushConnection() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getNetworkHandler() != null && client.getNetworkHandler().getConnection() != null) {
            client.getNetworkHandler().getConnection().flush();
        }
    }
}
