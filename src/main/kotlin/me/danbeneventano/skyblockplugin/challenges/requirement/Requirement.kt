package me.danbeneventano.skyblockplugin.challenges.requirement

import org.bukkit.entity.Player

abstract class Requirement {
    abstract fun check (player: Player)
}