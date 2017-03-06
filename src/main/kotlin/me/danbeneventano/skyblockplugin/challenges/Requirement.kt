package me.danbeneventano.skyblockplugin.challenges

import org.bukkit.Material
import org.bukkit.entity.EntityType

sealed class Requirement {
    data class BreakRequirement (val material: Material, val amount: Int) : Requirement()
    data class CollectRequirement (val material: Material, val amount: Int) : Requirement()
    data class KillRequirement (val entityType: EntityType, val amount: Int) : Requirement()
    data class PlaceRequirement (val material: Material, val amount: Int) : Requirement()
}