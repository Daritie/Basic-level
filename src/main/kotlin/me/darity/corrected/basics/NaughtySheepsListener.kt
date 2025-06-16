package me.darity.corrected.basics

import me.darity.corrected.basics.utils.*
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.entity.Sheep
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.util.Vector

object NaughtySheepsListener : Listener {
    private lateinit var plugin: Plugin

    fun init(plugin: Plugin) {
        this.plugin = plugin
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    private fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val sheeps = player.location.getNearbyEntitiesByType(Sheep::class.java, 1.5)
        if (sheeps.isEmpty()) return
        var vec = Vector(0, 0, 0)
        sheeps.forEach {
            vec += player.location.toVector() - it.location.toVector()
        }
        player.velocity = vec.multiply(0.1)
    }

    @EventHandler
    private fun onSheepDeath(event: EntityDeathEvent) {
        val entity = event.entity
        if (entity is Sheep && event.damageSource.directEntity is Player) {
            entity.world.dropItem(
                entity.location,
                ItemStack(Material.DIAMOND_SWORD).apply { addEnchantment(Enchantment.UNBREAKING, 1) }
            ) { swordEntity ->
                scheduler.runTaskLater(plugin, 2 * 20) {
                    swordEntity.takeIf { it.isValid }?.remove()
                }
            }
        }
    }
}