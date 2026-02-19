# NoDelayPackets

**Eliminate network jitter and optimize packet transmission in Minecraft 1.21.4.**

NoDelayPackets is a lightweight Fabric performance mod designed to minimize the latency between the client and server. By optimizing how packets are flushed through the network pipeline and enforcing low-level socket options, this mod ensures your inputs and actions reach the server with the absolute minimum delay allowed by your network hardware.

## Features

- **Immediate Packet Flushing**: Implements a high-priority hook into the Netty pipeline to force an immediate `channel.flush()` after every packet submission. This eliminates the "batching" or buffering delay often introduced by the operating system or the JVM.
- **Client-Side Optimization**: Designed specifically for the client-to-server connection to maximize responsiveness without adding unnecessary overhead to integrated or local servers.

## Technical Breakdown

For transparency and security, the following technical details describe how NoDelayPackets modifies the vanilla network stack:

1. **NetworkIoMixin**: Intercepts `net.minecraft.network.ClientConnection#send`. By modifying the transmission logic at the `TAIL` of the execution, it triggers a Netty flush immediately after the packet is written to the outbound buffer.
2. **Thread Safety**: All flushing operations are performed within the appropriate Netty event loop or validated against the channel state to ensure no race conditions or synchronization overhead.

## Compatibility

- **Krypton**: 100% compatible. While Krypton optimizes *how* data is serialized and managed, NoDelayPackets focuses purely on *when* that data is transmitted. The two mods work synergistically to provide the ultimate networking experience.
- **Fabric API**: Requires the latest Fabric API for 1.21.4.
- **Anticheat Friendly**: NoDelayPackets does not modify packet data, automate actions, or provide any unfair mechanical advantage. It purely optimizes the timing of existing vanilla communications.

## Integrity & Fair Play

NoDelayPackets is a performance-focused utility. It does **not**:
- Modify damage values or reach distances.
- Automate macros or multi-actions.
- Bypass server-side rate limits.
- Modify the content of packets sent to the server.

It is intended for players seeking a more responsive connection, particularly in high-stakes PvP scenarios where millisecond-level jitter can impact consistency.

---
*Developed by ReaQwQ*
