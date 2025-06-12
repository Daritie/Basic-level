package me.darity.basics.utils

import net.kyori.adventure.text.Component
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import org.bukkit.util.Vector

typealias Text = Component
operator fun Component.plus(other: Component) = append(other)

operator fun Vector.plus(other: Vector): Vector = this.add(other)
operator fun Vector.minus(other: Vector): Vector = this.subtract(other)

fun BukkitScheduler.runTaskLater(plugin: Plugin, delay: Long, task: () -> Unit): BukkitTask =
    runTaskLater(plugin, task, delay)

fun BukkitScheduler.runTaskTimer(plugin: Plugin, delay: Long, period: Long, task: () -> Unit): BukkitTask =
    runTaskTimer(plugin, task, delay, period)