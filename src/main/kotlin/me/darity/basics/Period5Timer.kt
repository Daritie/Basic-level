package me.darity.basics

import me.darity.basics.utils.*
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.entity.Player

// Task 12-13
fun launchingTimer() {
    var player: Player?
    var delta: Long
    for ((uuid, time) in Basics.launchTimes) {
        player = Bukkit.getServer().getPlayer(uuid)
        delta = time - System.currentTimeMillis()
        if (delta / 1000 <= 0) {
            player?.sendActionBar(Text.empty())
            Basics.launchTimes.remove(uuid)
        } else {
            player?.sendActionBar(Text.text("%02d:%02d".format(delta / 1000 / 60, delta / 1000 % 60)))
        }
    }
}

// Task 14
fun spawnTracks() {
    Basics.tracks.forEach { location ->
        location.world.spawnParticle(Particle.WAX_ON, location, 1)
    }
}