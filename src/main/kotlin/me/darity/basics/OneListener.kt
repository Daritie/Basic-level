package me.darity.basics

import com.destroystokyo.paper.event.server.ServerTickEndEvent
import me.darity.basics.Basics.Companion.scheduler
import me.darity.basics.utils.*
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.entity.Pig
import org.bukkit.entity.Player
import org.bukkit.entity.Sheep
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

object OneListener : Listener {
    @EventHandler
    fun task_1(event: PlayerJoinEvent) {
        event.player.sendRawMessage("abebe")
    }

    @EventHandler
    fun task_2(event: BlockBreakEvent) {
        if (event.block.type == Material.STONE) event.isCancelled = true
    }

    @EventHandler
    fun task_3(event: BlockPlaceEvent) {
        event.player.inventory.addItem(ItemStack(event.block.type))
    }

    @EventHandler
    fun task_4_5(event: PlayerInteractEvent) {
        val block = event.clickedBlock
        if (block?.type == Material.TALL_GRASS) {
            block.type = Material.AIR
            block.world.spawnEntity(block.location, EntityType.SHEEP)
        }
    }

    @EventHandler
    fun task_6(event: PlayerInteractEntityEvent) {
        (event.rightClicked as? Sheep)?.color = DyeColor.PURPLE
    }

    @EventHandler
    fun task_7(event: PlayerQuitEvent) {
        event.quitMessage(null)
        (event.player.world.spawnEntity(event.player.location, EntityType.PIG) as Pig).apply {
            setAI(false)
            Basics.entitiesToDelete[event.player.uniqueId]?.add(uniqueId)
                ?: run { Basics.entitiesToDelete[event.player.uniqueId] = mutableListOf(uniqueId) }
        }
    }

    @EventHandler
    fun task_8(event: PlayerJoinEvent) {
        Basics.entitiesToDelete[event.player.uniqueId]?.forEach {
            event.player.world.getEntity(it)?.remove()
        }
        Basics.entitiesToDelete.remove(event.player.uniqueId)

        event.joinMessage(event.joinMessage()?.color(NamedTextColor.GREEN))
    }

    @EventHandler
    fun task_9(event: PlayerMoveEvent) {
        var vec = Vector(0, 0, 0)
        // EntityType.SHEEP.entityClass или Sheep::class.java ?
        val sheeps = event.player.location.getNearbyEntitiesByType(EntityType.SHEEP.entityClass, 1.5)
        sheeps.forEach {
            vec += event.player.location.toVector() - it.location.toVector()
        }
        if (sheeps.isNotEmpty()) event.player.velocity = vec.multiply(0.1)
    }

    @EventHandler
    fun task_10_11(event: EntityDeathEvent) {
        if (event.entity is Sheep && event.damageSource.directEntity is Player) {
            event.entity.world.dropItem(
                event.entity.location,
                ItemStack(Material.DIAMOND_SWORD).apply { addEnchantment(Enchantment.UNBREAKING, 1) },
                { entity ->
                    scheduler.runTaskLater(Basics.instance, 2 * 20) { entity.remove() }
                }
            )
        }
    }

    @EventHandler
    fun task_12_13(event: PlayerToggleSneakEvent) {
        val delta = (Basics.launchTimes[event.player.uniqueId] ?: 0) - System.currentTimeMillis()
        if (event.isSneaking && delta <= 0) {
            Basics.launchTimes[event.player.uniqueId] = System.currentTimeMillis() + Basics.LAUNCH_DELAY
            scheduler.runTaskLater(Basics.instance, Basics.LAUNCH_DELAY) {
                event.player.sendActionBar(Text.empty())
                Basics.launchTimes.remove(event.player.uniqueId)
            }
            event.player.velocity += Vector(0, 5, 0)
        }
    }

    @EventHandler
    fun task_14(event: PlayerMoveEvent) {
        if (event.hasExplicitlyChangedPosition()) {
            val location = event.to
            Basics.tracks.add(location)
            event.player.world.spawnParticle(Particle.WAX_ON, location, 1)
            scheduler.runTaskLater(Basics.instance, 10 * 20) {
                event.player.world.spawnParticle(Particle.WAX_ON, location, 1)
                Basics.tracks.remove(location)
            }
        }
    }

    @EventHandler
    fun task_15(event: PlayerJoinEvent) {
        val prefix = Text.text("abebe")
        val displayName = prefix.appendSpace() + event.player.name()
        event.player.displayName(displayName)
        event.player.playerListName(displayName)
    }
}