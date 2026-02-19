# Packet Optimizer

**The Ultimate Low-Latency Engine for Minecraft 1.21.4 Crystal PvP.**

Packet Optimizer is a comprehensive performance suite designed to consolidate advanced networking, inventory, and combat optimizations into a single, high-performance engine. Built for the most demanding competitive environments, it ensures that your client operates with theoretical minimum latency and maximum synchronization.

## Features

### 1. Ping & Jitter Engine (No-Delay Core)
- **Aggressive Flushing**: Intercepts the network pipeline to force an immediate `channel.flush()` after every packet submission, bypassing vanilla tick-based batching.
- **Synchronous Dispatch**: Ensures that critical combat and movement packets are dispatched in the same network frame whenever possible.

### 2. Crystal Combat Optimizer
- **Break-Place Synchronization**: Optimizes the lifecycle of attack packets to ensure the "Break -> Place" cycle is perfectly fluid and not throttled by internal buffers.
- **Zero-Tick Entity Removal**: Processes entity removal signals from the server with zero-tick latency, allowing for faster subsequent placements without illegal exploits.

> [!IMPORTANT]
> **Technical Note on `target.discard()`**:
> This logic is purely for client-side visual and collision state synchronization. It does not automate any actions or send illegal packets. It is equivalent to the optimizations provided by well-known mods like Marlow's Crystal Optimizer, which are widely accepted in the competitive community. This ensures the client's internal world state accurately reflects the pending server-side removal, preventing "ghosting" artifacts.

### 3. Inventory Sync Optimizer
- **Totem Optimizer**: Specifically identifies Totem of Undying interactions and prioritizes their packet dispatch to ensure absolute reliability during critical defense moments.
- **Handshake Acceleration**: Prioritizes `ClickSlotC2SPacket` and `CloseScreenC2SPacket` with high-priority flushing to eliminate "ghost items."
- **Interaction Latency Reduction**: Minimizes the gap between a UI click and the transmission of the corresponding state-update packet.

### 4. S2C (Receive) Optimization
- **Early Packet Decoding**: Hooks into the Netty pipeline to begin decoding server-to-client packets (entity positions, explosions) immediately upon receipt, reducing perceived lag.

## Configuration

Packet Optimizer is fully configurable via `.minecraft/config/packet-optimizer.json`. All features can be toggled independently:
- `aggressiveFlush`: Toggles the immediate Netty channel flush.
- `tcpNoDelay`: Toggles the suppression of Nagle's Algorithm.
- `crystalOptimizer`: Toggles Marlow-style Zero-Tick interaction logic.
- `inventorySync`: Toggles priority inventory and hotkey packet dispatch.
- `earlyDecoder`: Toggles high-priority decode handling in the Netty pipeline.

## Technical Breakdown

For transparency and administrative review, Packet Optimizer utilizes the following architectural patterns:

1. **Netty Pipeline Injection**: Custom handlers and Mixins ensure that optimizations happen at the lowest possible level of the network stack.
2. **Context-Aware Flushing**: Logic is restricted to `CLIENTBOUND` connections (sending to server) to protect local server performance and prevent unnecessary overhead.
3. **Deterministic Logic**: All optimizations are based on accelerating vanilla patterns rather than modifying game state or automating user input.

## Fair Play & Integrity

Packet Optimizer is designed for **transparency** and **fairness**:
- **No Automation**: No macros, no auto-crystal, no automated sequences.
- **Anti-Cheat Safe**: Does not modify packet data or provide illegal mechanical advantages. It purely optimizes the timing of existing communications to reflect true player intent.
- **Krypton Compatible**: 100% compatible with Krypton’s serialization optimizations.

## Compatibility
- **Version**: Fabric 1.21.4
- **Dependencies**: Requires Fabric API.

---
*Developed by ReaQwQ*
