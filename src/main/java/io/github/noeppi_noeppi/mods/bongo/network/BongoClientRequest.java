package io.github.noeppi_noeppi.mods.bongo.network;

import io.github.noeppi_noeppi.mods.bongo.Bongo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.moddingx.libx.network.PacketHandler;
import org.moddingx.libx.network.PacketSerializer;

import java.util.UUID;
import java.util.Objects;
import java.util.function.Supplier;

public record BongoClientRequest(UUID playerId, BongoRequestType bongoRequestType) {
    
    public BongoClientRequest(UUID playerId) {
        this(playerId, BongoRequestType.GENERIC);
    }
    
    public static class Serializer implements PacketSerializer<BongoClientRequest> {

        @Override
        public Class<BongoClientRequest> messageClass() {
            return BongoClientRequest.class;
        }

        @Override
        public void encode(BongoClientRequest msg, FriendlyByteBuf buffer) {
            CompoundTag playerNbt = new CompoundTag();
            playerNbt.putUUID("player", msg.playerId);

            buffer.writeNbt(playerNbt);
            buffer.writeEnum(msg.bongoRequestType);
        }

        @Override
        public BongoClientRequest decode(FriendlyByteBuf buffer) {
            CompoundTag playerNbt = buffer.readNbt();
            UUID playerId = playerNbt.getUUID("player");

            return new BongoClientRequest(playerId, buffer.readEnum(BongoRequestType.class));
        }
    }
    
    public static class Handler implements PacketHandler<BongoClientRequest> {

        @Override
        public Target target() {
            return Target.MAIN_THREAD;
        }

        @Override
        public boolean handle(BongoClientRequest msg, Supplier<NetworkEvent.Context> ctx) {
            Bongo.handleClientRequest(msg.playerId, msg.bongoRequestType);
            return true;
        }
    }
}
