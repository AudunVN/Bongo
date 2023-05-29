package io.github.noeppi_noeppi.mods.bongo;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;

public class Keybinds {

    public static final KeyMapping BIG_OVERLAY = new KeyMapping("bongo.big_overlay", 'Y', "key.categories.ui");
    public static final KeyMapping TEAM_BACKPACK = new KeyMapping("bongo.team_backpack", 'B', "key.categories.multiplayer");

    public static void registerKeyBinds(RegisterKeyMappingsEvent event) {
        event.register(BIG_OVERLAY);
        event.register(TEAM_BACKPACK);
    }
}
