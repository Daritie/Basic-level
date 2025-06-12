package me.darity.basics

import me.darity.basics.utils.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler
import java.util.UUID

class Basics : JavaPlugin() {
    companion object {
        const val LAUNCH_DELAY: Long = 70 * 1000

        val entitiesToDelete = mutableMapOf<UUID, MutableList<UUID>>() // Player, Pigs
        val launchTimes = mutableMapOf<UUID, Long>() // Player, time
        val tracks = mutableListOf<Location>()
        val scheduler: BukkitScheduler
            get() = Bukkit.getScheduler()
        lateinit var instance: Basics
    }

    override fun onEnable() {
        instance = this
        Bukkit.getServer().pluginManager.registerEvents(OneListener, this)
        scheduler.runTaskTimer(this, 0, 5) {
            launchingTimer() // Task 12-13
            spawnTracks() // task 14
        }
    }
}