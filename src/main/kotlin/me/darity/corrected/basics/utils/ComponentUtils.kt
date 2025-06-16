package me.darity.corrected.basics.utils

import net.kyori.adventure.text.Component

typealias Text = Component
operator fun Component.plus(other: Component) = append(other)