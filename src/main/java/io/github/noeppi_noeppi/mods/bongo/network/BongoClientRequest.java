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

public record BongoClientRequest(Bongo bongo, BongoRequestType bongoRequestType) {
    
    public BongoClientRequest(Bongo bongo) {
        this(bongo, BongoRequestType.GENERIC);
    }
    
    public static class Serializer implements PacketSerializer<BongoClientRequest> {

        @Override
        public Class<BongoClientRequest> messageClass() {
            return BongoClientRequest.class;
        }

        @Override
        public void encode(BongoClientRequest msg, FriendlyByteBuf buffer) {
            buffer.writeNbt(msg.bongo.save(new CompoundTag()));
            buffer.writeEnum(msg.bongoRequestType);
        }

        @Override
        public BongoClientRequest decode(FriendlyByteBuf buffer) {
            Bongo bongo = new Bongo();
            bongo.load(Objects.requireNonNull(buffer.readNbt()));

            return new BongoClientRequest(bongo, buffer.readEnum(BongoRequestType.class));
        }
    }

    public static class Handler implements PacketHandler<BongoClientRequest> {

        @Override
        public Target target() {
            return Target.MAIN_THREAD;
        }

        @Override
        public boolean handle(BongoClientRequest msg, Supplier<NetworkEvent.Context> ctx) {
            ServerPlayer player = ctx.get().getSender();
            Bongo.handleClientRequest(msg.bongo, player, msg.bongoRequestType);
            return true;
        }
    }
}
