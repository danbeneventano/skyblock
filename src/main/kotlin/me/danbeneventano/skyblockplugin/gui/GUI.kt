package me.danbeneventano.skyblockplugin.gui

import me.danbeneventano.skyblockplugin.SkyblockPlugin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

class GUI (val plugin: SkyblockPlugin) {
    lateinit var inventory: Inventory
    val map = mutableMapOf<Int, GUIItem>()

    constructor (plugin: SkyblockPlugin, title: String, size: Int) : this(plugin) {
        inventory = Bukkit.createInventory(null, size, title)
    }

    constructor (plugin: SkyblockPlugin, title: String, type: InventoryType) : this(plugin) {
        inventory = Bukkit.createInventory(null, type, title)
    }

    init {
        plugin.server.pluginManager.registerEvents(GUIListener(this), plugin)
    }

    fun addItem (item: GUIItem) {
        inventory.setItem(item.position, item.itemStack)
        map[item.position] = item
    }

    class GUIListener (val gui: GUI) : Listener {
        @EventHandler fun onClick (event: InventoryClickEvent) {
            if (event.inventory === gui.inventory) {
                gui.map[event.slot]?.consumer?.accept(event)
                event.isCancelled = true
            }
        }
    }

}