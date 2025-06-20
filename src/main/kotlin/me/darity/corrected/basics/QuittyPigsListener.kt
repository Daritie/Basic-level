package me.darity.corrected.basics

import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Pig
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin
import java.util.UUID

object QuittyPigsListener : Listener {
    private val playerPigs = mutableMapOf<UUID, UUID>()

    fun init(plugin: Plugin) {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    private fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        event.quitMessage(null)
        player.world.spawn(player.location, Pig::class.java) { pig ->
            pig.setAI(false)
            playerPigs[player.uniqueId] = pig.uniqueId
        }
    }

    @EventHandler
    private fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        event.joinMessage(event.joinMessage()?.color(NamedTextColor.GREEN))
        playerPigs.remove(player.uniqueId)?.let { player.world.getEntity(it)?.remove() }
    }

}
