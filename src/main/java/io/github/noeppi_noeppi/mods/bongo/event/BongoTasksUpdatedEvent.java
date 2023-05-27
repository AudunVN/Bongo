package io.github.noeppi_noeppi.mods.bongo.event;

import io.github.noeppi_noeppi.mods.bongo.Bongo;
import io.github.noeppi_noeppi.mods.bongo.task.Task;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class BongoTasksUpdatedEvent extends Event {
    
    private final Bongo bongo;
    private final ServerLevel level;
    private final ServerPlayer player;

    public BongoTasksUpdatedEvent(Bongo bongo, ServerLevel level, ServerPlayer player) {
        this.bongo = bongo;
        this.level = level;
        this.player = player;
    }

    public Bongo getBongo() {
        return bongo;
    }

    public ServerLevel getLevel() {
        return level;
    }

    public ServerPlayer getPlayer() {
        return player;
    }
}
