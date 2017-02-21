package me.danbeneventano.skyblockplugin.challenges.challenges

import me.danbeneventano.skyblockplugin.challenges.Challenge
import me.danbeneventano.skyblockplugin.challenges.requirement.RqBreak
import me.danbeneventano.skyblockplugin.challenges.rewards.RwItem
import me.danbeneventano.skyblockplugin.challenges.rewards.RwUserExp
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ChCobble : Challenge(name = "Cobblestone Miner",
        requirements = listOf(RqBreak(Material.COBBLESTONE, 64)),
        rewards = listOf(RwUserExp(50), RwItem(ItemStack(Material.SAND, 5)))
)