package com.reaqwq.packetoptimizer.mixin.receive;

import com.reaqwq.packetoptimizer.config.PacketOptimizerConfig;
import net.minecraft.network.ClientConnection;
import io.netty.channel.ChannelHandlerContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;

@Mixin(ClientConnection.class)
public class EarlyDecoderMixin {
    @Inject(method = "channelRead", at = @At("HEAD"))
    private void onChannelRead(ChannelHandlerContext ctx, Object msg, CallbackInfo ci) {
        if (PacketOptimizerConfig.getInstance().earlyDecoder) {
            if (msg instanceof ExplosionS2CPacket || msg instanceof EntityPositionS2CPacket) {
                ctx.fireChannelRead(msg);
                ci.cancel();
            }
        }
    }
}
