package me.danbeneventano.skyblockplugin.challenges.rewards

import org.bukkit.entity.Player

abstract class Reward {
    abstract fun give (player: Player)
}