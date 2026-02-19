package com.reaqwq.packetoptimizer.mixin.receive;

import com.reaqwq.packetoptimizer.config.PacketOptimizerConfig;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import io.netty.channel.ChannelHandlerContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class EarlyDecoderMixin {
    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead0(ChannelHandlerContext ctx, Packet<?> packet, CallbackInfo ci) {
        if (PacketOptimizerConfig.getInstance().earlyDecoder) {
            if (packet instanceof ExplosionS2CPacket explosion) {
                com.reaqwq.packetoptimizer.PacketOptimizer.getInstance().playExplosionEffects(explosion);
                ctx.fireChannelRead(packet);
                ci.cancel();
            } else if (packet instanceof EntityPositionS2CPacket) {
                ctx.fireChannelRead(packet);
                ci.cancel();
            }
        }
    }
}
