package me.darity.corrected.basics

import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Sheep
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

object BasicActionsWithBlocksAndEntitiesListener : Listener {
    private lateinit var plugin: Plugin

    fun init(plugin: Plugin) {
        this.plugin = plugin
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    private fun onJoin(event: PlayerJoinEvent) {
        event.player.sendRawMessage("abebe")
    }

    @EventHandler
    private fun onBreakStone(event: BlockBreakEvent) {
        if (event.block.type == Material.STONE) event.isCancelled = true
    }

    @EventHandler
    private fun onPlaceBlock(event: BlockPlaceEvent) {
        event.player.inventory.addItem(ItemStack(event.block.type))
    }

    @EventHandler
    private fun onTouchGrass(event: PlayerInteractEvent) {
        val block = event.clickedBlock
        if (block?.type == Material.TALL_GRASS) {
            block.type = Material.AIR
            block.world.spawnEntity(block.location, EntityType.SHEEP)
        }
    }

    @EventHandler
    private fun onTouchSheep(event: PlayerInteractEntityEvent) {
        (event.rightClicked as? Sheep)?.color = DyeColor.PURPLE
    }
}