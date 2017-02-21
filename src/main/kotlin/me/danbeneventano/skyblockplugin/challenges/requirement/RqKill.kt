package me.danbeneventano.skyblockplugin.challenges.requirement

import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

class RqKill(val entityType: EntityType, val amount: Int) : Requirement() {
    override fun check(player: Player) {

    }
}