package com.reaqwq.packetoptimizer.mixin.network;

import com.reaqwq.packetoptimizer.config.PacketOptimizerConfig;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import io.netty.channel.Channel;
import org.jetbrains.annotations.Nullable;

@Mixin(ClientConnection.class)
public abstract class NetworkIoMixin {
    @Shadow
    private Channel channel;

    @Shadow
    private NetworkSide side;

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;Z)V", at = @At("TAIL"))
    private void onSend(Packet<?> packet, @Nullable PacketCallbacks callbacks, boolean flush, CallbackInfo ci) {
        if (this.side == NetworkSide.CLIENTBOUND && this.channel != null && this.channel.isOpen()) {
            PacketOptimizerConfig config = PacketOptimizerConfig.getInstance();
            if (!config.aggressiveFlush)
                return;

            if (packet instanceof ClickSlotC2SPacket || packet instanceof PlayerInteractEntityC2SPacket) {
                this.channel.flush();
            } else if (packet instanceof PlayerMoveC2SPacket) {
                this.channel.flush();
            }
        }
    }
}
