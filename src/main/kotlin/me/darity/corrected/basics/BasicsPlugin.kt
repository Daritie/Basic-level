package me.darity.corrected.basics

import org.bukkit.plugin.java.JavaPlugin

class BasicsPlugin : JavaPlugin() {
    companion object {
        lateinit var instance: BasicsPlugin
    }

    override fun onEnable() {
        instance = this

        BasicActionsWithBlocksAndEntitiesListener.init(this) // Задания 1-6
        QuittyPigsListener.init(this) // Задания 7-8
        NaughtySheepsListener.init(this) // Задания 9-11
        PlayerLaunchListener.init(this) // Задания 12-13
        MagicTracksListener.init(this) // Задание 14
        ChatDecorationsListener.init(this) // Задание 15
    }
}