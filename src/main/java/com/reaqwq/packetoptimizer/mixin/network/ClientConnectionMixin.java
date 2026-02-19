package com.reaqwq.packetoptimizer.mixin.network;

import com.reaqwq.packetoptimizer.config.PacketOptimizerConfig;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(method = "addHandlers(Lio/netty/channel/ChannelPipeline;Lnet/minecraft/network/NetworkSide;Z)V", at = @At("TAIL"))
    private void onAddHandlers(ChannelPipeline pipeline, NetworkSide side, boolean transitions, CallbackInfo ci) {
        if (side == NetworkSide.CLIENTBOUND && PacketOptimizerConfig.getInstance().tcpNoDelay) {
            pipeline.channel().config().setOption(ChannelOption.TCP_NODELAY, true);
        }
    }
}
