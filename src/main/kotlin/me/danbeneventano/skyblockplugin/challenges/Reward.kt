package me.danbeneventano.skyblockplugin.challenges

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

sealed class Reward {
    abstract fun give (player: Player)
    class ExperienceReward (val amount: Int) : Reward() {
        override fun give(player: Player) {
            player.totalExperience += amount
        }
    }
    class ItemReward (val item: ItemStack) : Reward() {
        override fun give(player: Player) {
            if (player.inventory.firstEmpty() == -1) {
                player.location.world.dropItemNaturally(player.location, item)
            } else {
                player.inventory.addItem(item)
            }
        }
    }
}