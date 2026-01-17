package dev.phntm;

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.phntm.events.MapEvents;

import javax.annotation.Nonnull;

/** Plugin that hides player icons on the world map. */
public class HidePlayersOnMapPlugin extends JavaPlugin {

    public HidePlayersOnMapPlugin(@Nonnull final JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        getEventRegistry().registerGlobal(PlayerReadyEvent.class, MapEvents::onPlayerReady);
        MapEvents.applyToExisting();
    }

    @Override
    protected void shutdown() {
        MapEvents.reset();
    }
}
