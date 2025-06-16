package me.darity.corrected.basics

import me.darity.corrected.basics.utils.*
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.plugin.Plugin
import org.bukkit.util.Vector
import java.util.UUID

object PlayerLaunchListener : Listener {
    private const val LAUNCH_DELAY = 70 * 20
    private val launchTimes = mutableMapOf<UUID, Int>()

    fun init(plugin: Plugin) {
        scheduler.runTaskTimer(plugin, 0, 5) { launchingTimer() }
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    private fun onSneak(event: PlayerToggleSneakEvent) {
        val player = event.player
        val delta = (launchTimes[player.uniqueId] ?: 0) - Bukkit.getCurrentTick()
        if (event.isSneaking && delta <= 0) {
            launchTimes[player.uniqueId] = Bukkit.getCurrentTick() + LAUNCH_DELAY
            scheduler.runTaskLater(BasicsPlugin.instance, LAUNCH_DELAY.toLong()) {
                launchTimes.remove(player.uniqueId)
                player.takeIf { it.isValid }?.sendActionBar(Text.empty())
            }
            player.velocity += Vector(0, 5, 0)
        }
    }

    private fun launchingTimer() {
        for ((uuid, time) in launchTimes) {
            val player = BasicsPlugin.instance.server.getPlayer(uuid) ?: continue
            val delta = time - Bukkit.getCurrentTick()
            if (delta <= 0) {
                player.sendActionBar(Text.empty())
                launchTimes.remove(uuid)
            } else {
                player.sendActionBar(Text.text("%02d:%02d".format(delta / 20 / 60, delta / 20 % 60)))
            }
        }
    }
}