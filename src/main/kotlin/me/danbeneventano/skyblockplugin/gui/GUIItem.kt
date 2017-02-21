package me.danbeneventano.skyblockplugin.gui

import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

data class GUIItem(val position: Int, val itemStack: ItemStack, val consumer: Consumer<InventoryClickEvent>) {

    constructor (coordinates: Pair<Int, Int>, itemStack: ItemStack, consumer: Consumer<InventoryClickEvent>) :
            this ((coordinates.first) + (coordinates.second * 9), itemStack, consumer)

    companion object {
        fun from (material: Material, amount: Int, name: String, vararg lore: String): ItemStack {
            val stack = ItemStack(material, amount)
            val meta = stack.itemMeta
            meta.displayName = name
            meta.lore = lore.toList()
            stack.itemMeta = meta
            return stack
        }
    }
}