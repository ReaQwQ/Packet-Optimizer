package com.reaqwq.packetoptimizer.mixin.receive;

import com.reaqwq.packetoptimizer.config.PacketOptimizerConfig;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class EarlyDecoderMixin {
    @Unique
    private static long lastExplosionTime = 0;

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead0(ChannelHandlerContext ctx, Packet<?> packet, CallbackInfo ci) {
        if (!PacketOptimizerConfig.getInstance().earlyDecoder)
            return;
        if (packet instanceof ExplosionS2CPacket explosion) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastExplosionTime >= 100) {
                lastExplosionTime = currentTime;
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.world != null) {
                    client.execute(() -> {
                        double x = explosion.center().x;
                        double y = explosion.center().y;
                        double z = explosion.center().z;
                        client.world.addParticle(ParticleTypes.EXPLOSION, x, y, z, 1.0, 0.0, 0.0);
                        client.world.playSound(x, y, z,
                                SoundEvents.ENTITY_GENERIC_EXPLODE.value(),
                                SoundCategory.BLOCKS, 4.0F,
                                (1.0F + (client.world.random.nextFloat() - client.world.random.nextFloat()) * 0.2F)
                                        * 0.7F,
                                false);
                    });
                }
            }
            ctx.fireChannelRead(packet);
            ci.cancel();

        } else if (packet instanceof EntityPositionS2CPacket) {
            ctx.fireChannelRead(packet);
            ci.cancel();
        }
    }
}
