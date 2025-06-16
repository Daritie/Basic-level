package me.darity.corrected.basics.utils

import org.bukkit.util.Vector

operator fun Vector.plus(other: Vector): Vector = this.add(other)
operator fun Vector.minus(other: Vector): Vector = this.subtract(other)