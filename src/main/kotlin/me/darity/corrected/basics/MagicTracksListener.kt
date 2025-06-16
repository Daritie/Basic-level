package me.darity.corrected.basics

import me.darity.corrected.basics.utils.*
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.plugin.Plugin

object MagicTracksListener : Listener {
    private const val TRACKS_REMOVING_DELAY: Long = 10 * 20
    private val tracks = mutableListOf<Location>()

    fun init(plugin: Plugin) {
        scheduler.runTaskTimer(plugin, 0, 5) { spawnTracks() }
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    private fun onMove(event: PlayerMoveEvent) {
        if (event.hasExplicitlyChangedPosition()) {
            val location = event.to
            val world = location.world
            tracks.add(location)
            world.spawnParticle(Particle.WAX_ON, location, 1)
            scheduler.runTaskLater(BasicsPlugin.instance, TRACKS_REMOVING_DELAY) {
                world.spawnParticle(Particle.WAX_ON, location, 1)
                tracks.remove(location)
            }
        }
    }

    private fun spawnTracks() {
        tracks.forEach { location ->
            location.world.spawnParticle(Particle.WAX_ON, location, 1)
        }
    }
}