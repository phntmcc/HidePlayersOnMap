package dev.phntm.events;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/** Hides player icons on the world map. */
public class MapEvents {

    private static final Set<World> processedWorlds = ConcurrentHashMap.newKeySet();

    /** Clears state on plugin shutdown/reload. */
    public static void reset() {
        processedWorlds.clear();
    }

    /** Applies filters to all existing worlds and players. Call on plugin setup for hot-swap support. */
    @SuppressWarnings("removal")
    public static void applyToExisting() {
        for (final World world : Universe.get().getWorlds().values()) {
            removeMarkerProviders(world);
            world.getPlayers().forEach(MapEvents::hidePlayerMarkers);
        }
    }

    /** Called when a player joins. Removes marker providers and sets player filters. */
    public static void onPlayerReady(@Nonnull final PlayerReadyEvent event) {
        final Player player = event.getPlayer();
        if (player == null) {
            return;
        }

        final World world = getWorldFromEvent(event);
        if (world == null) {
            return;
        }

        removeMarkerProviders(world);
        hidePlayerMarkers(player);
    }

    /** Gets World instance from PlayerReadyEvent via EntityStore. */
    private static World getWorldFromEvent(@Nonnull final PlayerReadyEvent event) {
        try {
            final Ref<EntityStore> playerRef = event.getPlayerRef();
            if (playerRef == null) {
                return null;
            }

            final EntityStore entityStore = playerRef.getStore().getExternalData();
            if (entityStore == null) {
                return null;
            }

            return entityStore.getWorld();
        } catch (Exception e) {
            // Internal API access - failures expected if API changes
            return null;
        }
    }

    /** Removes all player-related marker providers. Processes each world once. */
    private static void removeMarkerProviders(@Nonnull final World world) {
        if (!processedWorlds.add(world)) {
            return;
        }

        final WorldMapManager mapManager = world.getWorldMapManager();
        if (mapManager == null) {
            return;
        }

        final Map<String, WorldMapManager.MarkerProvider> providers = mapManager.getMarkerProviders();
        if (providers == null) {
            return;
        }

        providers.remove("playerIcons");
        providers.remove("playerMarkers");
    }

    /** Sets player filter to hide all players on the map. */
    private static void hidePlayerMarkers(@Nonnull final Player player) {
        final WorldMapTracker tracker = player.getWorldMapTracker();
        if (tracker == null) {
            return;
        }

        tracker.setPlayerMapFilter(pRef -> false);
    }
}
