package me.danbeneventano.skyblockplugin.challenges.requirement

import org.bukkit.Material
import org.bukkit.entity.Player

class RqCollect(val material: Material, val amount: Int) : Requirement() {
    override fun check(player: Player) {

    }
}