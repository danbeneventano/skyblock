package me.danbeneventano.skyblockplugin.user

import org.bukkit.Material
import org.bukkit.entity.EntityType

data class UserStats (
        val kills: Map<EntityType, Int> = mutableMapOf(),
        val blocksMined: Map<Material, Int> = mutableMapOf(),
        val itemsPickedUp: Map<Material, Int> = mutableMapOf(),
        val blocksPlaced: Map<Material, Int> = mutableMapOf()
)