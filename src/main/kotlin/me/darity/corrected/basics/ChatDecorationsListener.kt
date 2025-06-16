package me.darity.corrected.basics

import io.papermc.paper.event.player.AsyncChatEvent
import me.darity.corrected.basics.utils.*
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin

object ChatDecorationsListener : Listener {
    private val DEFAULT_PREFIX = Text.text("abebe").color(NamedTextColor.RED)
    
    fun init(plugin: Plugin) {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    private fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val displayName = Text.empty()
            .append(DEFAULT_PREFIX)
            .appendSpace()
            .append(player.name())
        player.displayName(displayName)
        player.playerListName(displayName)
    }

    @EventHandler
    private fun onChat(event: AsyncChatEvent) {
        event.renderer { player, displayName, message, viewer ->
            Text.empty()
                .append(displayName)
                .append(Text.text(": "))
                .append(message)
        }
    }
}
